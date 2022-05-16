package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Lobby {
    private static Lobby instance;
    private final HashMap<String, Integer> players;
    private final HashMap<Integer, Room> initializingRooms;
    private final HashMap<Integer, Room> playingRooms;
    private final HashMap<Integer, RoomInfo> infos;
    private final AtomicBoolean listenEnd;
    private ServerSocket socket;
    private final Random random;

    //todo: modify 9999 with value from a config file
    private Lobby(){
        players = new HashMap<>();
        initializingRooms = new HashMap<>();
        playingRooms = new HashMap<>();
        infos = new HashMap<>();
        random = new Random();
        listenEnd = new AtomicBoolean(false);

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
        while(!listenEnd.get()){
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

    /**
     * @return a copy of currently playing players
     */
    public Map<String, Integer> getPlayers() {
        synchronized (players) {
            return new HashMap<>(players);
        }
    }

    /**
     * Inserts a player in players only if the username is not being used at the moment
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
            if(!(initializingRooms.isEmpty()))
                return false;
        }

        listenEnd.set(true);
        Socket fake = new Socket("localhost", 9999);
        fake.close();
        return true;
    }

    /**
     * @return true if lobby can accept new players, false otherwise
     */
    public boolean isRunning(){
        return !listenEnd.get();
    }

    public Room createRoom(int numberOfPlayers, boolean expert, boolean isPrivate, ClientHandler ch){
        int id;

        synchronized (infos){
            do {
                id = random.nextInt() % 1000000;
            } while (infos.containsKey(id));

            infos.put(id, new RoomInfo(id, numberOfPlayers, expert, isPrivate));
        }

        Room room = new Room(id, numberOfPlayers, expert);
        synchronized (initializingRooms){
            initializingRooms.put(id, room);
        }
        addToRoom(id, ch);
        return room;
    }

    boolean addToRoom(int roomId, ClientHandler ch){
        String player = ch.getUsername();
        synchronized (players){
            if(!players.containsKey(player) || players.get(player) != null)
                return false;

            if(!initializingRooms.containsKey(roomId))
                return false;

            getFromInitializing(roomId).addPlayer(player, ch);
            players.put(player, roomId);
        }
        return true;
    }

    Room getFromInitializing(int id){
        return initializingRooms.get(id);
    }

    void moveToPlayingRooms(int id){
        synchronized (initializingRooms){
            playingRooms.put(id, getFromInitializing(id));
            initializingRooms.remove(id);
        }
    }

    /**
     * @return a copy of all active rooms' information
     */
    public Map<Integer, RoomInfo> getInfos() {
        synchronized (infos){
            return new HashMap<>(infos);
        }
    }

    void eliminateRoom(int id){
        synchronized (infos){
            synchronized (initializingRooms){
                initializingRooms.remove(id);
                playingRooms.remove(id);
            }
            infos.remove(id);
        }
    }
}
