package it.polimi.ingsw.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String username = null;

    ClientHandler(Socket client){
        this.client = client;
    }

    public void run(){

        try {
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println("could not open connection to " + client.getInetAddress());
            return;
        }

        System.out.println("Connected to " + client.getInetAddress());


        while(username == null){
            try{
                Object next = input.readObject();
            } catch (ClassNotFoundException | ClassCastException  e) {
                System.out.println("Could not get Login message from client at " + client.getInetAddress());
            }

        }
    }

    private void handleClientConnection() throws IOException{

    }

}
