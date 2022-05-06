package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String username = null;
    private final Lobby lobby = Lobby.getInstance();

    ClientHandler(Socket client){
        this.client = client;
    }

    public void run(){

        try {
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            if(lobby.isRunning())
                System.out.println("could not open connection to " + client.getInetAddress());
            return;
        }

        System.out.println("Connected to " + client.getInetAddress());

        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");
        }

        try {
            client.close();
        } catch (IOException e) { }
    }

    private void handleClientConnection() throws IOException{
        Message message;
        login();

        boolean logout = false;
        // todo: complete
        while(!logout){

            try{
                message = (Message) input.readObject();
            } catch (ClassNotFoundException | ClassCastException  e) {
                output.writeObject(new Nack("Not a message"));
                continue;
            }

            if(message.getMessageType() == MessageType.LOGOUT){
                if(lobby.getPlayers().get(username) != null)
                    output.writeObject(new Nack("Player still in a room"));

                lobby.removePlayer(username);
                logout = true;
                output.writeObject(new Ack());
            }
        }
    }

    /**
     * Listens for a Login message at the start of a connection. It loops until a unique username is given.
     * @throws IOException if the input/output stream does not function properly
     */
    private void login() throws IOException{
        while(username == null){
            try{
                username = ((Login) input.readObject()).getUsername();
            } catch (ClassNotFoundException | ClassCastException  e) {
                output.writeObject(new Nack("Login message missing"));
                continue;
            }

            if(lobby.insertNewPlayer(username)) {
                output.writeObject(new Ack());
                return;
            }

            // resets loop condition
            username = null;
            output.writeObject(new Nack("Username was already taken"));
        }
    }
}
