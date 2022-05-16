package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import it.polimi.ingsw.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ClientHandler implements Runnable{
    private final Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String username = null;
    private final Lobby lobby = Lobby.getInstance();
    private Room room = null;

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
        } catch (IOException | UnexpectedMessageException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");
        }

        try {
            client.close();
        } catch (IOException e) {
            // todo: fill with something
        }
    }

    private void handleClientConnection() throws IOException, UnexpectedMessageException{
        Message message;
        login();

        boolean logout = false;
        while(!logout){

            try{
                message = (Message) input.readObject();
            } catch (ClassNotFoundException | ClassCastException  e) {
                output.writeObject(new Nack("Not a message"));
                continue;
            }

            switch (message.getMessageType()){
                case LOGIN:
                    output.writeObject(new Nack("Already logged in!"));
                    break;

                case GET_PUBLIC_ROOMS:
                    getPublicRooms((GetPublicRooms) message);
                    break;

                case CREATE_ROOM:
                    if(room != null){
                        output.writeObject(new Nack("Already in a room!"));
                        break;
                    }
                    createRoom((CreateRoom) message);
                    break;

                case ACCESS_ROOM:
                    if(room != null){
                        output.writeObject(new Nack("Already in a room!"));
                        break;
                    }
                    accessRoom((AccessRoom) message);
                    break;

                case LEAVE_ROOM:
                    if(room != null){
                        output.writeObject(new Nack("Not yet in a room!"));
                        break;
                    }
                    leaveRoom();
                    break;

                case GAME_EVENT:
                    //todo
                    break;

                case LOGOUT:
                    logout = logout();
                    break;
                default:
                    throw new UnexpectedMessageException("Not a correct message");
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

    private void getPublicRooms(GetPublicRooms message) throws IOException{
        List<RoomInfo> result = new ArrayList<>();

        for(RoomInfo info : lobby.getInfos().values()){
            if(complies(info, message))
                result.add(info);
        }

        output.writeObject(new PublicRooms(result));
    }

    private boolean complies(RoomInfo info, GetPublicRooms message){
        if(info.isFull() || info.isPrivate())
            return false;

        if(message.getNumberOfPlayers() == -1)
            return message.getExpert() == null || message.getExpert() == info.getExpert();

        if(message.getExpert() == null)
            return message.getNumberOfPlayers() == info.getNumberOfPlayers();

        return (message.getNumberOfPlayers() == info.getNumberOfPlayers() && message.getExpert() == info.getExpert());
    }

    private void createRoom(CreateRoom m) throws IOException{
        room = lobby.createRoom(m.numberOfPlayers(), m.expert(), m.isPrivate());
        lobby.addToRoom(room.getId(), this);
        output.writeObject(new RoomId(room.getId()));
    }

    private void accessRoom(AccessRoom m) throws IOException{
        room = lobby.getFromInitializing(m.id());
        if(room == null){
            output.writeObject(new Nack("Not an initializing room"));
        }

        if (lobby.addToRoom(m.id(), this)) {
            output.writeObject(new Ack());
        } else {
            room = null;
            output.writeObject(new Nack("Unable to access room"));
        }
    }

    private void leaveRoom() throws IOException{
        if(room.removePlayer()){
            room = null;
            output.writeObject(new Ack());
            return;
        }
        output.writeObject(new Nack("Cannot leave the room at the moment"));
    }

    private boolean logout() throws IOException{
        if(lobby.getPlayers().get(username) != null) {
            output.writeObject(new Nack("Player still in a room"));
            return false;
        }

        lobby.removePlayer(username);
        output.writeObject(new Ack());
        return true;
    }

    public void sendMessage(Message message)throws IOException {
        output.writeObject(message);
    }

    public String getUsername() {
        return username;
    }
}
