package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.Login;
import it.polimi.ingsw.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class NetworkHandler implements Runnable{
    Socket server;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public NetworkHandler(String serverIP, int serverPort) throws IOException {
        server = new Socket(serverIP, serverPort);
    }

    public void run(){
        try {
            output = new ObjectOutputStream(server.getOutputStream());
            input = new ObjectInputStream(server.getInputStream());
        } catch (IOException e) {
            System.out.println("could not open connection to server at" + server.getInetAddress());
            return;
        }
    }

    //todo: get input from cli/gui
    private void login() throws IOException{
        Scanner scanner = new Scanner(System.in);
        String username;
        boolean unique = true;
        Message answer;

        while(unique){
            System.out.println("Insert unique name: ");
            username = scanner.nextLine();

            output.writeObject(new Login(username));

            try{
                answer = (Message) input.readObject();
            } catch (ClassNotFoundException | ClassCastException  e){
                return;
            }
        }

    }
}
