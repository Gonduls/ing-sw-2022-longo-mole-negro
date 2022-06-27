package it.polimi.ingsw.server;

import it.polimi.ingsw.Log;
import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import it.polimi.ingsw.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all communication with the single client it is assigned to
 */
public class ClientHandler implements Runnable{
    private final Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String username = null;
    private final Lobby lobby = Lobby.getInstance();
    private Room room = null;

    /**
     * Sets the client parameters
     */
    ClientHandler(Socket client){
        this.client = client;

        try {
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            if(lobby.isRunning())
                System.out.println("could not open connection to " + client.getInetAddress());
            return;
        }

        System.out.println("Connected to " + client.getInetAddress() + "/" + client.getPort());
    }

    /**
     * Calls handleClientConnection and waits for it to be done, or listens for Exceptions.
     * Then it removes the player from the lobby
     */
    public void run(){
        try {
            handleClientConnection();
            client.close();
            System.out.println("client " + client.getInetAddress() + "/" + client.getPort() + " disconnected");
        } catch (IOException | UnexpectedMessageException e) {
            System.out.println("client " + client.getInetAddress() + "/" + client.getPort() + " connection dropped");
            if(room != null)
                room.playerDisconnect(this);
        }

        lobby.removePlayer(username);
    }

    /**
     * Calls login, then, while the player is logged in,
     * loops listening for messages and calling functions that deal with them
     * @throws IOException if the input/output stream does not function properly
     * @throws UnexpectedMessageException If a message that was not supposed to be received is received
     */
    private void handleClientConnection() throws IOException, UnexpectedMessageException{
        Message message;
        login();

        boolean logout = false;
        while(!logout){

            try{
                message = (Message) input.readObject();
                Log.logger.info(message.getMessageType().toString());
            } catch (ClassNotFoundException | ClassCastException  e) {
                output.writeObject(new Nack("Not a message"));
                continue;
            }

            if(MessageType.isServerMessage(message.getMessageType()))
                throw new UnexpectedMessageException("Did not receive a client message");

            switch (message.getMessageType()) {
                case LOGIN -> output.writeObject(new Nack("Already logged in!"));
                case GET_PUBLIC_ROOMS -> getPublicRooms((GetPublicRooms) message);
                case CREATE_ROOM -> {
                    if (room != null) {
                        output.writeObject(new Nack("Already in a room!"));
                        break;
                    }
                    createRoom((CreateRoom) message);
                }
                case ACCESS_ROOM -> {
                    if (room != null) {
                        output.writeObject(new Nack("Already in a room!"));
                        break;
                    }
                    accessRoom(((AccessRoom) message).id());
                }
                case LEAVE_ROOM -> {
                    if (room == null) {
                        output.writeObject(new Nack("Not yet in a room!"));
                        break;
                    }
                    leaveRoom();
                }
                case GAME_EVENT -> {
                    if (room == null) {
                        output.writeObject(new Nack("Not yet in a room!"));
                        break;
                    }
                    try {
                        room.getRoundController().handleEvent((GameEvent) message);
                    } catch (Exception e) {
                        output.writeObject(new Nack(e.getMessage()));
                        break;
                    }
                    output.writeObject(new Ack());
                }
                case LOGOUT -> logout = logout();
                default -> throw new UnexpectedMessageException("Not a correct message");
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

    /**
     * For every room in public rooms, checks if it is compatible with the GetPublicRooms message received.
     * It then creates a PublicRooms message with all compatible rooms, and sends it to the player
     * @param message The GetPublicRooms message that specifies which public rooms to show
     * @throws IOException if the input/output stream does not function properly
     */
    private void getPublicRooms(GetPublicRooms message) throws IOException{
        List<RoomInfo> result = new ArrayList<>();

        for(RoomInfo info : lobby.getInfos().values()){
            if(isCompatible(info, message)) {
                result.add(info);
            }
        }

        output.writeObject(new PublicRooms(result));
    }

    /**
     * It checks if the RoomInfo given is compatible with the GetPublicRooms message
     * @param info The RoomInfo to check
     * @param message The GetPublicRooms message that hold the criteria
     * @return True if the RoomInfo is compatible, false otherwise
     */
    private boolean isCompatible(RoomInfo info, GetPublicRooms message){
        if(info.isFull() || info.isPrivate())
            return false;

        if(message.getNumberOfPlayers() == -1)
            return message.getExpert() == null || message.getExpert() == info.getExpert();

        if(message.getExpert() == null)
            return message.getNumberOfPlayers() == info.getNumberOfPlayers();

        return (message.getNumberOfPlayers() == info.getNumberOfPlayers() && message.getExpert() == info.getExpert());
    }

    /**
     * Calls Lobby.createRoom() and sends to the roomId back to the player
     * @param m The CreateRoom message with room creation parameters
     * @throws IOException if the input/output stream does not function properly
     */
    private void createRoom(CreateRoom m) throws IOException{
        room = lobby.createRoom(m.numberOfPlayers(), m.expert(), m.isPrivate());
        lobby.addToRoom(room.getId(), this);
        output.writeObject(new RoomId(room.getId()));
    }

    /**
     * Calls lobby.addToRoom() if possible, else sends Nack to the player.
     * If player was properly added to the room it sends an Ack back, otherwise a Nack is sent
     * @param id The identifier of the room that is to be accessed
     * @throws IOException if the input/output stream does not function properly
     */
    private void accessRoom(int id) throws IOException{
        room = lobby.getFromInitializing(id);
        if(room == null){
            output.writeObject(new Nack("Not an initializing room"));
            return;
        }

        if (lobby.addToRoom(id, this)) {
            output.writeObject(new Ack());
        } else {
            room = null;
            output.writeObject(new Nack("Unable to access room"));
        }
    }

    /**
     * It disconnects the player from the room he was in
     * @throws IOException if the input/output stream does not function properly
     */
    private void leaveRoom() throws IOException{
        room.playerDisconnect(this);
        room = null;
        lobby.addToRoom(0, this);
        output.writeObject(new Ack());
    }

    /**
     * If the player is not in a room, removes player from lobby and terminates handleClientConnection()
     * @return True if logout was successful, false otherwise
     * @throws IOException if the input/output stream does not function properly
     */
    private boolean logout() throws IOException{
        if(lobby.getPlayers().get(username) != null) {
            output.writeObject(new Nack("Player still in a room"));
            return false;
        }

        lobby.removePlayer(username);
        output.writeObject(new Ack());
        return true;
    }

    /**
     * It sends any given message to the player
     * @param message The message to be sent
     * @throws IOException if the input/output stream does not function properly
     */
    public void sendMessage(Message message)throws IOException {
        output.writeObject(message);
    }

    /**
     * @return The player Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * It sets the room to null
     */
    public void disconnectFromRoom(){
        room = null;
    }
}
