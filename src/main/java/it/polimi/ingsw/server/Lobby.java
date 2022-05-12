package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

public class Lobby {
    private static Lobby instance;
    private final HashMap<String, Integer> players;
    private final HashMap<Integer, Room> publicInitializingRooms;
    private final HashMap<Integer, Room> privateInitializingRooms;
    private final HashMap<Integer, Room> playingRooms;
    private final HashMap<Integer, RoomInfo> infos;
    private boolean listenEnd = false;
    private ServerSocket socket;
    private final Random random;


    //todo: modify 9999 with value from a config file
    private Lobby(){
        players = new HashMap<>();
        publicInitializingRooms = new HashMap<>();
        privateInitializingRooms = new HashMap<>();
        playingRooms = new HashMap<>();
        infos = new HashMap<>();
        random = new Random();

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

    public int createRoom(int numberOfPlayers, boolean expert, boolean isPrivate, ClientHandler ch){
        int id;

        synchronized (infos){
            do {
                id = random.nextInt() % 1000000;
            } while (infos.containsKey(id));

            infos.put(id, new RoomInfo(id, numberOfPlayers, expert));
        }

        Room room = new Room(id, numberOfPlayers, expert);

        if(isPrivate)
            privateInitializingRooms.put(id, room);
        else
            publicInitializingRooms.put(id, room);

        addToRoom(id, ch);
        return id;
    }

    boolean addToRoom(int roomId, ClientHandler ch){
        String player = ch.getUsername();
        synchronized (players){
            if(!players.containsKey(player) || players.get(player) != null)
                return false;

            if(!publicInitializingRooms.containsKey(roomId) && ! privateInitializingRooms.containsKey(roomId))
                return false;

            getFromPublicOrPrivate(roomId).addPlayer(player, ch);
            players.put(player, roomId);
        }
        return true;
    }

    private Room getFromPublicOrPrivate(int id){
        if(!publicInitializingRooms.containsKey(id)){
            return (privateInitializingRooms.getOrDefault(id, null));
        }
        return publicInitializingRooms.get(id);
    }

    void moveToPlayingRooms(int id){
        playingRooms.put(id, getFromPublicOrPrivate(id));
        publicInitializingRooms.remove(id);
        privateInitializingRooms.remove(id);
    }


    public HashMap<Integer, RoomInfo> getInfos() {
        return new HashMap<>(infos);
    }
}
