package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Lobby {
    private static Lobby instance;
    private final HashMap<String, Integer> players;
    private final HashMap<Integer, Room> publicInitializingRooms;
    private final HashMap<Integer, Room> privateInitializingRooms;
    private final HashMap<Integer, Room> PlayingRooms;
    private final boolean listenEnd = true;
    private ServerSocket socket;

    //todo: modify 9999 with value from a config file
    private Lobby(){
        players = new HashMap<>();
        publicInitializingRooms = new HashMap<>();
        privateInitializingRooms = new HashMap<>();
        PlayingRooms = new HashMap<>();

        try {
            socket = new ServerSocket(9999);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }
    }

    public static Lobby getInstance(){
        if(instance == null)
            instance = new Lobby();

        return instance;
    }

    public void listen(){

        while(listenEnd){
            try{
                Socket client = socket.accept();

                //ClientHandler clientHandler = new ClientHandler(client);
                //Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                //thread.start();
            }
            catch (IOException e) {
                System.out.println("connection dropped");
            }


        }
    }

}
