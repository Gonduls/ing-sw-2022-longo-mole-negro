package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.RoundController;
import it.polimi.ingsw.messages.AddPlayer;
import it.polimi.ingsw.messages.Message;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Room {
    private final int id;
    private final String[] players;
    private final ClientHandler[] handlers;
    private final boolean expert;
    private int present = 0;
    private final AtomicBoolean canLeave;
    private final RoundController rc;
    private final RoomInfo info;

    Room(int id, int numberOfPlayers, boolean expert){
        this.id = id;
        this.expert = expert;
        players = new String[numberOfPlayers];
        handlers = new ClientHandler[numberOfPlayers];
        rc = new RoundController();
        info = Lobby.getInstance().getInfos().get(id);
        canLeave = new AtomicBoolean(false);
    }

    int getId() {
        return id;
    }

    void addPlayer(String player, ClientHandler ch){
        if(present == players.length)
            return;

        for(int i = 0; i< present; i++){
            try{
                ch.sendMessage(new AddPlayer(players[i], i));
            } catch (IOException e){
                return;
            }
        }

        try{
            sendBroadcast(new AddPlayer(player, present));
        }catch (IOException e){
            return;
        }

        players[present] = player;
        handlers[present] = ch;
        info.addPlayer();
        present++;

        // todo: tell someone that everything can begin
        if(present == players.length)
            Lobby.getInstance().moveToPlayingRooms(id);
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

    public void sendBroadcast(Message message) throws IOException {
        for(ClientHandler ch : handlers)
            if(ch != null)
                ch.sendMessage(message);
    }
}
