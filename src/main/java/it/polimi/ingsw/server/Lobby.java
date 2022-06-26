package it.polimi.ingsw.server;

import it.polimi.ingsw.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Main server class, keeps track of all players and rooms
 */
public class Lobby {
    private static Lobby instance;
    private final HashMap<String, Integer> players = new HashMap<>();
    private final HashMap<Integer, Room> initializingRooms = new HashMap<>();
    private final HashMap<Integer, Room> playingRooms = new HashMap<>();
    private final HashMap<Integer, RoomInfo> infos = new HashMap<>();
    private final AtomicBoolean listenEnd = new AtomicBoolean(false);
    private ServerSocket socket;
    private final Random random = new Random();

    /**
     * Creates a socket
     * @param port The port the server will listen on
     */
    private Lobby(int port){
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
        }
    }

    /**
     * @return The one and only instance of Lobby, with port = 9999 if not created beforehand
     */
    public static Lobby getInstance(){
        return getInstance(9999);
    }

    /**
     * Using Singleton pattern, returns the one and only instance of Lobby, creating a socket on port
     * @param port The port the server will listen on
     * @return The one and only instance of Lobby, with port = 'port' if not created beforehand
     */
    public static Lobby getInstance(int port){
        if(instance == null)
            instance = new Lobby(port);

        return instance;
    }

    /**
     * While stop has not been called, loops accepting new clients and associating them with new network handlers
     */
    public void listen(){
        new Log("Server.txt");
        System.out.println("Starting listen");
        while(!listenEnd.get()){
            try{
                Socket client = socket.accept();

                ClientHandler clientHandler = new ClientHandler(client);
                Thread thread = new Thread(clientHandler);
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

    /**
     * Creates a room and corresponding RoomInfo, with no player in it
     * @param numberOfPlayers The number of players that will populate the room
     * @param expert The difficulty of the game
     * @param isPrivate True if the room is private, false otherwise
     * @return The room created
     */
    public Room createRoom(int numberOfPlayers, boolean expert, boolean isPrivate){
        int id;

        synchronized (infos){
            // assures a 4 digit id
            do {
                id = (random.nextInt(8999) + 1000);
            } while (infos.containsKey(id));

            infos.put(id, new RoomInfo(id, numberOfPlayers, expert, isPrivate));
        }

        Room room = new Room(id, numberOfPlayers, expert);
        synchronized (initializingRooms){
            initializingRooms.put(id, room);
        }
        return room;
    }

    /**
     * Attempts to add a player into a room.
     * This operation fails, and false is returned, if the player is already inside a room or if the room is already full.
     * If the roomId is 0 then the player is removed from any room he was in
     * @param roomId The room the player has to be put in, 0 if the player is to be removed from a room
     * @param ch The Client handler that handel the player
     * @return True if the player was successfully added to the room
     */
    boolean addToRoom(int roomId, ClientHandler ch){
        String player = ch.getUsername();
        synchronized (players){
            if(!players.containsKey(player) || players.get(player) != null)
                return false;

            if(roomId == 0){
                players.put(ch.getUsername(), null);
                return true;
            }

            if(!initializingRooms.containsKey(roomId))
                return false;

            getFromInitializing(roomId).addPlayer(ch);
            players.put(player, roomId);
        }
        return true;
    }

    /**
     * Returns a Room if it was present in the initializing rooms
     * @param id The identifier of the room
     * @return The Room if present, null otherwise
     */
    Room getFromInitializing(int id){
        return initializingRooms.get(id);
    }

    /**
     * Deletes a room from initializingRooms and adds it to PlayingRooms
     * @param id The identifier of the room
     */
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

    /**
     * Eliminates a room and any player that was in that room no longer is in a room
     * @param id The identifier of the room
     */
    void eliminateRoom(int id){
        synchronized (infos){
            synchronized (initializingRooms){
                initializingRooms.remove(id);
                playingRooms.remove(id);
            }
            infos.remove(id);
        }

        synchronized(players){
            players.replaceAll((p, v) -> (players.get(p) == null || players.get(p) == id) ? null : players.get(p));
        }
    }
}
