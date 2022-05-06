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
    private final HashMap<Integer, Room> playingRooms;
    private boolean listenEnd = false;
    private ServerSocket socket;

    //todo: modify 9999 with value from a config file
    private Lobby(){
        players = new HashMap<>();
        publicInitializingRooms = new HashMap<>();
        privateInitializingRooms = new HashMap<>();
        playingRooms = new HashMap<>();

        try {
            socket = new ServerSocket(9999);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
        }
    }

    public static Lobby getInstance(){
        if(instance == null)
            instance = new Lobby();

        return instance;
    }

    public void listen(){
        while(!listenEnd){
            try{
                Socket client = socket.accept();

                ClientHandler clientHandler = new ClientHandler(client);
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                thread.start();
            }
            catch (IOException e) {
                System.out.println("connection dropped");
            }
        }

        System.out.println("Shutting down player acceptance");
    }

    public HashMap<String, Integer> getPlayers() {
        return players;
    }

    /**
     * Inserts a player in players only if the username is unique
     *
     * @param username: the username to insert
     * @return true if the username was unique and was inserted, false if the username was already in players
     */
    public boolean insertNewPlayer(String username){
        synchronized (players){
            if(players.containsKey(username))
                return false;

            players.put(username, null);
            return true;
        }
    }

    public void removePlayer(String username){
        synchronized (players){
            players.remove(username);
        }
    }

    /**
     * Stops the lobby from accepting new players,
     * but does not stop players from playing already started games.
     * If any games were still initializing, it returns false and does nothing.
     *
     * @return true if no rooms were in the midst of initializing, false otherwise (before closing the lobby)
     * @throws IOException if problems occur in creating and/or closing a "fake" client socket
     */
    public boolean stop() throws IOException{
        synchronized (players){
            if(!(publicInitializingRooms.isEmpty() && privateInitializingRooms.isEmpty()))
                return false;
        }

        listenEnd = true;
        Socket fake = new Socket("localhost", 9999);
        fake.close();
        return true;
    }

    /**
     * @return true if lobby can accept new players, false otherwise
     */
    public boolean isRunning(){
        return !listenEnd;
    }
}
