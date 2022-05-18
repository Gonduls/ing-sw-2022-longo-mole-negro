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
            } catch (ClassNotFoundException | ClassCastException e) {
                e.printStackTrace();
                return;
            } catch (IOException e){
                clientController.showMessage(new PlayerDisconnect("Current user, please close everything and start over"));
                return;
            }

            if(MessageType.isClientMessage(answer.getMessageType())){
                clientController.showMessage(new PlayerDisconnect("Current user, please close everything and start over"));
                return;
            }
            // Tries to feed the answer message to the clientController.
            // If that fails, the answer must be read by another function in network handler
            synchronized (lockAnswer){
                if(answer.getMessageType() == MessageType.PUBLIC_ROOMS){
                    clientController.showPublicRooms(((PublicRooms) answer).getRooms());
                    answer = null;
                    continue;
                }
                else if(answer.getMessageType() == MessageType.PLAYER_DISCONNECT){
                    clientController.showMessage(answer);
                    answer = null;
                    continue;
                }
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
     * Synchronizes functions on lockAnswer, sends the passed message to the server and receives an Ack/Nack answer.
     * Every function that needs an Ack/Nack answer has to first call occupy.
     *
     * @param message The message to be written in the output stream
     * @return The ack / nack message
     * @throws IOException if the input/output stream are not correctly set up
     * @throws UnexpectedMessageException if a different message from ack/nack is received
     */
    Message occupy(Message message) throws IOException, UnexpectedMessageException{
        if(occupied.get())
            return new Nack("Wait until other function is done");

        occupied.set(true);

        synchronized (lockAnswer) {
            output.writeObject(message);
            lockAnswer.notifyAll();

            do  {
                try {
                    lockAnswer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    occupied.set(false);
                    return new Nack("Errors in the execution of wait, closing thread");
                }
            } while(answer == null);

            occupied.set(false);
            Message toReturn = answer;

            if (answer.getMessageType() == MessageType.NACK) {
                answer = null;
                lockAnswer.notifyAll();
                return toReturn;
            }
            if (answer.getMessageType() == MessageType.ACK) {
                answer = null;
                lockAnswer.notifyAll();
                return toReturn;
            }
            throw new UnexpectedMessageException("A different message from ack or nack was read");
        }
    }

    /**
     * Login client side function.
     * It calls occupy with login message, if nack is returned no login has happened.
     * No login can happen if the username provided is already being used by another player.
     * This function has to be called before calling any other function of NetworkHandler
     * right after starting the thread.
     *
     * @param username a unique name that will identify the player
     * @return true if the login action has been completed, false otherwise
     * @throws IOException if the input/output stream are not correctly set up
     * @throws UnexpectedMessageException if a different message from ack/nack is received
     */
    boolean login(String username) throws IOException, UnexpectedMessageException {
        Message returnValue = occupy(new Login(username));

        if(returnValue.getMessageType() == MessageType.ACK) {
            canLogOut = true;
            getPublicRooms(new GetPublicRooms());
            return true;
        }

        return false;
    }

    /**
     * Logout client side function.
     * It calls occupy with logout message, if nack is returned no logout has happened.
     * It also does it if no logout operation can be performed at the time of calling (like mid-game).
     * If logout is correctly executed, the thread will stop as a result and the username will no longer be in use,
     * therefore it can be used by other players.
     *
     * @return true if logout was successful, false otherwise
     * @throws IOException if the input/output stream are not correctly set up
     * @throws UnexpectedMessageException if a different message from ack/nack is received
     */
    boolean logout() throws IOException, UnexpectedMessageException{
        if(!canLogOut)
            return false;

        Message returnValue = occupy(new Logout());

        if(returnValue.getMessageType() == MessageType.ACK){
            endCondition = true;
            server.close();
            return true;
        }

        return false;
    }

    /**
     * Writes the GetPublicRooms message in the output stream, then returns.
     * The answer message will be read from "run" and be dealt with in ClientController
     * @param message The GetPublicRooms Message to be written in the output stream
     * @throws IOException if the input/output stream are not correctly set up
     */
    void getPublicRooms(GetPublicRooms message) throws IOException{
        output.writeObject(message);
    }

    boolean accessRoom(int id) throws IOException, UnexpectedMessageException{
        Message returnValue = occupy(new AccessRoom(id));
        return returnValue.getMessageType() == MessageType.ACK;
    }

    int createRoom(CreateRoom message) throws IOException, UnexpectedMessageException{
        synchronized (lockAnswer){
            output.writeObject(message);
            do{
                try {
                    answer = (Message) input.readObject();
                } catch (ClassNotFoundException | ClassCastException | IOException e) {
                    e.printStackTrace();
                    return 0;
                }
                if(answer.getMessageType() == MessageType.PUBLIC_ROOMS)
                    clientController.showPublicRooms(((PublicRooms) answer).getRooms());
                else if(answer.getMessageType() == MessageType.PLAYER_DISCONNECT){
                    clientController.showMessage(answer);
                    continue;
                }else if (answer.getMessageType() == MessageType.NACK)
                    return 0;

                if(answer.getMessageType() != MessageType.ROOM_ID)
                    throw new UnexpectedMessageException("Not a RoomId message");
            }while (answer.getMessageType() == MessageType.ROOM_ID);

            return ((RoomId) answer).id();
        }
    }

    boolean leaveRoom() throws IOException, UnexpectedMessageException{
        return occupy(new LeaveRoom()).getMessageType() == MessageType.ACK;
    }

    /**
     * It calls occupy with event message, if nack is returned no event has happened.
     * The consequences of a correctly executed event are a nack and the asynchronous update of all models
     *
     * @param event: the game event that will be written in the output stream
     * @return nack if occupied was set to true, or the ack/nack response that the event generated
     * @throws IOException if the input/output stream are not correctly set up
     * @throws UnexpectedMessageException if a different message from ack/nack is received
     */
    Message performEvent(GameEvent event) throws IOException, UnexpectedMessageException{
        if(!clientController.myTurn())
            return new Nack("Can't execute game event: not your turn");

        return occupy(event);
    }
}
