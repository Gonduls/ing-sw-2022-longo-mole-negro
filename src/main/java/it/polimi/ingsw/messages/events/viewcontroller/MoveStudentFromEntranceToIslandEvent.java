package it.polimi.ingsw.messages.events.viewcontroller;

import it.polimi.ingsw.model.Color;

public class MoveStudentFromEntranceToIslandEvent extends VC_GameEvent {


    String playerName;
    Color color;
    int indexIsland;


    public MoveStudentFromEntranceToIslandEvent(String playerName, Color color, int indexIsland) {
        this.playerName = playerName;
        this.color = color;
        this.indexIsland = indexIsland;
    }

    @Override
    public GameEventType getEventType() {
        return null;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    public Color getColor(){
       return color;
    }

    public int getIndexIsland() {
        return indexIsland;
    }


}
