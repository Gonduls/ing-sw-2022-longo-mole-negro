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
        } catch (IOException e) { }
    }

    private void handleClientConnection() throws IOException, UnexpectedMessageException{
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

            switch (message.getMessageType()){
                case GET_PUBLIC_ROOMS:
                    getPublicRooms((GetPublicRooms) message);
                    break;

                case CREATE_ROOM:
                    CreateRoom m = (CreateRoom) message;
                    int id = lobby.createRoom(m.getNumberOfPlayers(), m.isExpert(), m.isPrivate(), this);
                    output.writeObject(new RoomId(id));
                    break;

                case ACCESS_ROOM:
                    AccessRoom m1 = (AccessRoom) message;
                    if (lobby.addToRoom(m1.getId(), this)) {
                        output.writeObject(new Ack());
                    } else {
                        output.writeObject(new Nack("Unable to access room"));
                    }
                    break;

                case LEAVE_ROOM:
                    break;

                case GAME_EVENT:


                case LOGOUT:
                    if(lobby.getPlayers().get(username) != null)
                        output.writeObject(new Nack("Player still in a room"));

                    lobby.removePlayer(username);
                    logout = true;
                    output.writeObject(new Ack());
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

    public void getPublicRooms(GetPublicRooms message) throws IOException{
        List<RoomInfo> result = new ArrayList<>();

        for(RoomInfo info : lobby.getInfos().values()){
            if(complies(info, message))
                result.add(info);
        }

        output.writeObject(new PublicRooms(result));
    }

    private boolean complies(RoomInfo info, GetPublicRooms message){
        if(info.isFull())
            return false;

        if(message.getNumberOfPlayers() == -1)
            return message.getExpert() == null || message.getExpert() == info.getExpert();

        if(message.getExpert() == null)
            return message.getNumberOfPlayers() == info.getNumberOfPlayers();

        return (message.getNumberOfPlayers() == info.getNumberOfPlayers() && message.getExpert() == info.getExpert());
    }

    public void sendMessage(Message message)throws IOException {
        output.writeObject(message);
    }

    public String getUsername() {
        return username;
    }
}
