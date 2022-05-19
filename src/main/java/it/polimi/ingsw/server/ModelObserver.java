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
    public void moveFromEntranceToIsland(int player, Color color ,int islandIndex){
        room.sendBroadcast(new MoveStudent("ENTRANCE:"+player, "ISLAND:" + islandIndex, color));
    }

    public void moveFromEntranceToTable(int player, Color color){
        room.sendBroadcast(new MoveStudent("ENTRANCE:"+player, "DININGROOM:" + player, color));
    }

    public void moveMotherNature(int amount){
        room.sendBroadcast(new MoveMotherNature(amount));
    }
}
