package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MoveMotherNature;
import it.polimi.ingsw.messages.MoveStudent;
import it.polimi.ingsw.model.*;

import java.io.IOException;

public class ModelObserver {
    Room room;

    public ModelObserver(Room room){
        this.room=room;
    }

    //move student from a player entrance to an island
    public void moveFromEntranceToIsland(String playerName, Color color ,int islandIndex){
       try {
           room.sendBroadcast(new MoveStudent());
       } catch(IOException e) {
           //boh
            }

    }

    public void moveFromEntranceToTable(String playerName, Color color){
        try {
            room.sendBroadcast(new MoveStudent());
        } catch(IOException e) {
            //boh
        }
    }



    public void moveMotherNature(int amount){
        try {
            room.sendBroadcast(new MoveMotherNature());
        } catch(IOException e) {
            //boh
        }
    }




}
