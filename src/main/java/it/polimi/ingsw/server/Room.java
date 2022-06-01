package it.polimi.ingsw.server;

import it.polimi.ingsw.Log;
import it.polimi.ingsw.controller.RoundController;
import it.polimi.ingsw.messages.AddPlayer;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.PlayerDisconnect;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Room {
    private final int id;
    private final String[] players;
    private final ClientHandler[] handlers;
    private final boolean expert;
    private int present = 0;
    private final AtomicBoolean canLeave;
    private RoundController rc;
    private final RoomInfo info;
    private final Log log;

    Room(int id, int numberOfPlayers, boolean expert){
        this.id = id;
        this.expert = expert;
        players = new String[numberOfPlayers];
        handlers = new ClientHandler[numberOfPlayers];
        info = Lobby.getInstance().getInfos().get(id);
        canLeave = new AtomicBoolean(false);
        log = new Log("Room" + id + ".txt");
    }

    int getId() {
        return id;
    }

    void addPlayer(String player, ClientHandler ch){

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
        log.logger.info("present = " + present + " adding player: " + player);


        info.addPlayer();
        present++;

        if(present == players.length) {
            Lobby.getInstance().moveToPlayingRooms(id);
            rc = new RoundController(players, expert, this);
        }
    }

    boolean removePlayer(){
        if(canLeave.get()){
            info.removePlayer();

            if(present == 1){
                Lobby.getInstance().eliminateRoom(id);
            }
            else
                present --;

            return true;
        }
        return false;
    }

    public void setCanLeave(boolean value){
        canLeave.set(value);
    }

    public void sendBroadcast(Message message) {
        log.logger.info(message.toString());
        for(ClientHandler ch : handlers) {
            if (ch != null) {
                try {
                    ch.sendMessage(message);
                } catch (IOException e) {
                    log.logger.warning("Could not send message to: " + ch.getUsername());
                }
            }
        }
    }

    RoundController getRoundController(){
        return rc;
    }

    void playerDisconnect( ClientHandler handler){
        for(int i = 0; i< players.length; i++){
            if(handlers[i] == null)
                break;

            if (handlers[i] == handler)
                handlers[i] = null;
            else
                handlers[i].diconnectFromRoom();
        }

        Lobby.getInstance().eliminateRoom(id);
        sendBroadcast(new PlayerDisconnect(handler.getUsername()));
    }
}
