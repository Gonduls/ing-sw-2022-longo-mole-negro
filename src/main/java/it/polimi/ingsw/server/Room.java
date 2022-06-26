package it.polimi.ingsw.server;

import it.polimi.ingsw.Log;
import it.polimi.ingsw.controller.RoundController;
import it.polimi.ingsw.messages.AddPlayer;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.PlayerDisconnect;

import java.io.IOException;

/**
 * All games are played in a Room
 * Handles broadcast notification, disconnections and the game controller
 */
public class Room {
    private final int id;
    private final String[] players;
    private final ClientHandler[] handlers;
    private final boolean expert;
    private int present = 0;
    private RoundController rc;
    private final RoomInfo info;

    /**
     * Creates a room setting starting parameters
     * @param id The room identifier
     * @param numberOfPlayers The number of players the game will have
     * @param expert The difficulty of the game
     */
    Room(int id, int numberOfPlayers, boolean expert){
        this.id = id;
        this.expert = expert;
        players = new String[numberOfPlayers];
        handlers = new ClientHandler[numberOfPlayers];
        info = Lobby.getInstance().getInfos().get(id);
    }

    /**
     * @return The room identifier
     */
    int getId() {
        return id;
    }

    /**
     * Adds a player to the room, notifying all other players.
     * When the last player is added, a roundController is created and the room is moved to playingRooms
     * @param ch The handler that controls the player to be added
     */
    void addPlayer(ClientHandler ch){
        String player = ch.getUsername();
        for(int i = 0; i< present; i++){
            try{
                ch.sendMessage(new AddPlayer(players[i], i));
            } catch (IOException e){
                return;
            }
        }

        players[present] = player;
        handlers[present] = ch;

        sendBroadcast(new AddPlayer(player, present));
        Log.logger.info("present = " + present + " adding player: " + player);

        info.addPlayer();
        present++;

        if(present == players.length) {
            Lobby.getInstance().moveToPlayingRooms(id);
            rc = new RoundController(players, expert, this);
        }
    }

    /**
     * Sends the passed message to all clientHandler currently present
     * @param message The message to send
     */
    public void sendBroadcast(Message message) {
        Log.logger.info(message.toString());

        if(message.getMessageType() == MessageType.END_GAME) {
            System.out.println("Eliminating room");
            Lobby.getInstance().eliminateRoom(id);
            for(ClientHandler h : handlers){
                if(h!= null)
                    h.disconnectFromRoom();
            }
        }

        for(ClientHandler ch : handlers) {
            if (ch != null) {
                try {
                    ch.sendMessage(message);
                } catch (IOException e) {
                    Log.logger.warning("Could not send message to: " + ch.getUsername());
                }
            }
        }
    }

    /**
     * @return The roundController associated with the game, null if none were created yet
     */
    RoundController getRoundController(){
        return rc;
    }

    /**
     * Disconnects all players from room, sending everyone but a PlayerDisconnect message,
     * Deletes the room and associated info
     * @param handler The handler of the player that disconnected
     */
    void playerDisconnect(ClientHandler handler){
        for(int i = 0; i< players.length; i++){
            if(handlers[i] == null)
                continue;

            if (handlers[i] == handler)
                handlers[i] = null;
            else
                handlers[i].disconnectFromRoom();
        }

        Lobby.getInstance().eliminateRoom(id);
        Lobby.getInstance().getInfos().remove(id);
        sendBroadcast(new PlayerDisconnect(handler.getUsername()));
    }
}
