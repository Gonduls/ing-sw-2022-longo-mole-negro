package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import it.polimi.ingsw.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetworkHandler implements Runnable{
    private final Socket server;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;
    private boolean canLogOut = false;
    private Message answer = null;
    private final Object lockAnswer = new Object();
    private final AtomicBoolean occupied = new AtomicBoolean(false);
    private boolean endCondition = false;
    private final ClientController clientController;

    /**
     * Sets up the client-server connection
     *
     * @param serverIP the server IP address
     * @param serverPort the server Port
     * @param clientController the client side controller, that receives model-update messages
     * @throws IOException if no connection or input/output stream can be created with the server
     */
    public NetworkHandler(String serverIP, int serverPort, ClientController clientController) throws IOException {
        server = new Socket(serverIP, serverPort);
        output = new ObjectOutputStream(server.getOutputStream());
        input = new ObjectInputStream(server.getInputStream());
        this.clientController = clientController;
    }

    /**
     * Thread function of NetworkController.
     * Until an endCondition is set, if follows the schema:
     * wait until answer is null ->
     * read new message ->
     * give the message to the client controller (if able) and set it to null ->
     * start over.
     *
     * The only time that another function can read the content of the message is while run() is waiting.
     */
    public void run() {
        while (!endCondition){

            // Checks that answer has been read correctly by other functions
            synchronized (lockAnswer){
                while (answer != null) {
                    lockAnswer.notifyAll();

                    try {
                        lockAnswer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }

            if(endCondition)
                return;

            // Reads the next message
            try {
                answer = (Message) input.readObject();
            } catch (ClassNotFoundException | ClassCastException | IOException e) {
                e.printStackTrace();
                return;
            }

            // Tries to feed the answer message to the clientController.
            // If that fails, the answer must be read by another function in network handler
            synchronized (lockAnswer){
                try {
                    clientController.updateCModel(answer);
                    answer = null;
                } catch (UnexpectedMessageException e) {
                    lockAnswer.notifyAll();
                }
            }
        }
    }


    /**
     * Login client side function.
     * Like all other functions in NetworkHandler, if login is called while another
     * function of NetworkHandler (apart from run()) is still running, it wil return false and not perform any action.
     * It also does it if the username provided is already being used by another player.
     * This function has to be called first, along with starting the thread.
     *
     * @param username a unique name that will identify the player
     * @return true if the login action has been completed, false otherwise
     * @throws IOException if the input/output stream are not correctly set up
     * @throws UnexpectedMessageException if a different message from ack/nack is received
     */
    public boolean login(String username) throws IOException, UnexpectedMessageException {
        if(occupied.get())
            return false;
        occupied.set(true);

        synchronized (lockAnswer){
            output.writeObject(new Login(username));
            lockAnswer.notifyAll();

            while (answer == null) {
                try {
                    lockAnswer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    occupied.set(false);
                    return false;
                }
            }
            occupied.set(false);

            if (answer.getMessageType() == MessageType.NACK) {
                answer = null;
                lockAnswer.notifyAll();
                return false;
            }
            if (answer.getMessageType() == MessageType.ACK) {
                answer = null;
                canLogOut = true;
                lockAnswer.notifyAll();
                return true;
            }
            throw new UnexpectedMessageException("A different message from ack or nack was read");
        }
    }

    /**
     * Logout client side function.
     * Like all other functions in NetworkHandler, if logout is called while another
     * function of NetworkHandler (apart from run()) is still running, it wil return false and not perform any action.
     * It also does it if no logout operation can be performed at the time of calling (like mid-game).
     * If logout is correctly executed, the thread will stop as a result and the username will no longer be in use,
     * therefore it can be used by other players.
     *
     * @return true if logout was successful, false otherwise
     * @throws IOException if the input/output stream are not correctly set up
     * @throws UnexpectedMessageException if a different message from ack/nack is received
     */
    public boolean logout() throws IOException, UnexpectedMessageException{
        if(occupied.get())
            return false;

        if(!canLogOut)
            return false;

        occupied.set(true);
        synchronized (lockAnswer){
            output.writeObject(new Logout());
            lockAnswer.notifyAll();

            while(answer == null){
                try {
                    lockAnswer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    return false;
                }
            }

            occupied.set(false);

            if(answer.getMessageType() == MessageType.NACK){
                answer = null;
                lockAnswer.notifyAll();
                return false;
            }
            if(answer.getMessageType() == MessageType.ACK){
                answer = null;
                endCondition = true;
                server.close();
                lockAnswer.notifyAll();
                return true;
            }
            throw new UnexpectedMessageException("A different message from ack or nack was read");
        }
    }
}
