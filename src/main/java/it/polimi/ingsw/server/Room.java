package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.RoundController;
import it.polimi.ingsw.messages.AddPlayer;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.Nack;

import javax.print.DocFlavor;
import java.io.IOException;

public class Room {
    private final int id;
    private final String[] players;
    private final ClientHandler[] handlers;
    private final boolean expert;
    private int present = 0;
    private boolean canLeave = false;
    private final RoundController rc;

    Room(int id, int numberOfPlayers, boolean expert){
        this.id = id;
        this.expert = expert;
        players = new String[numberOfPlayers];
        handlers = new ClientHandler[numberOfPlayers];
        rc = new RoundController();
    }

    int getId() {
        return id;
    }

    int addPlayer(String player, ClientHandler ch){
        for(int i = 0; i< present; i++){
            try{
                ch.sendMessage(new AddPlayer(players[i], i));
            } catch (IOException e){
                return -1;
            }
        }

        try{
            sendBroadcast(new AddPlayer(player, present));
        }catch (IOException e){
            return -1;
        }

        players[present] = player;
        handlers[present] = ch;

        synchronized (Lobby.getInstance().getInfos()){
            Lobby.getInstance().getInfos().get(id).addPlayer();
        }

        present++;

        if(present == players.length) {
            Lobby.getInstance().moveToPlayingRooms(id);
            canLeave = false;
        }
        else if (players.length > 1) {
            canLeave = true;
        }

        return present - 1;
    }

    Message removePlayer(String player){
        return new Nack("todo");
    }

    public void sendBroadcast(Message message) throws IOException {
        for(ClientHandler ch : handlers)
            if(ch != null)
                ch.sendMessage(message);
    }


}
