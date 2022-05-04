package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import it.polimi.ingsw.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkHandler implements Runnable{
    Socket server;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    //private String username;
    private boolean canLogOut = true;
    private Message answer = null;
    private final Object lockAnswer = new Object();
    private final Object lockAckNack = new Object();
    private boolean endCondition = false;
    private ClientController cController;

    public NetworkHandler(String serverIP, int serverPort, ClientController cController) throws IOException {
        server = new Socket(serverIP, serverPort);
        try {
            output = new ObjectOutputStream(server.getOutputStream());
            input = new ObjectInputStream(server.getInputStream());
        } catch (IOException e) {
            System.out.println("could not open connection to server at" + server.getInetAddress());
            return;
        }

        this.cController = cController;
    }

    public void run() {
        while (!endCondition){
            synchronized (lockAnswer){
                if (answer != null) {
                    try {
                        lockAnswer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }

                try {
                    answer = (Message) input.readObject();
                } catch (ClassNotFoundException | ClassCastException | IOException e) {
                    e.printStackTrace();
                    return;
                }

                try {
                    cController.updateCModel(answer);
                } catch (UnexpectedMessageException e) {
                    lockAnswer.notifyAll();
                }
            }

        }
    }


    public boolean login(String usernameTemp) throws IOException, UnexpectedMessageException {
        synchronized (lockAckNack) {
            synchronized (lockAnswer) {
                while(answer != null){
                    try {
                        lockAnswer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                output.writeObject(new Login(usernameTemp));
                lockAnswer.notifyAll();

                try {
                    lockAnswer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return false;
                }

                if(answer.getMessageType() == MessageType.NACK){
                    answer = null;
                    lockAnswer.notifyAll();
                    return false;
                }
                if(answer.getMessageType() == MessageType.ACK){
                    //this.username = usernameTemp;
                    answer = null;
                    lockAnswer.notifyAll();
                    return true;
                }

                throw new UnexpectedMessageException("A different message from ack or nack was read");
            }
        }
    }

    /**
     * If the client is at a state where he can log out, performs logout
     *
     * @return true if logout was successful, false otherwise
     * @throws IOException if an improper message was sent from the server
     */
    public boolean logOut() throws IOException{
        if(!canLogOut)
            return false;

        output.writeObject(new Logout());

        try{
            answer = (Message) input.readObject();
        } catch (ClassNotFoundException | ClassCastException  e){
            throw(new IOException("Could not get a proper response from the server"));
        }

        if(answer.getMessageType() == MessageType.ACK){
            endCondition = true;
            return true;
        }

        if(answer.getMessageType() == MessageType.NACK){
            System.out.println("Could not log out");
            return false;
        }

        return false;
    }
}
