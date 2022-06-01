package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.Color;

public class MoveStudentFromEntranceToIslandEvent extends GameEvent {


    String playerName;
    Color color;
    int indexIsland;

    int playerNumber;


    public MoveStudentFromEntranceToIslandEvent(Color color, int indexIsland, int playerNumber) {
        this.color = color;
        this.indexIsland = indexIsland;
        this.playerNumber = playerNumber;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.MOVE_STUDENT_FROM_ENTRANCE_TO_ISLAND;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }

    public Color getColor(){
       return color;
    }

    public int getIndexIsland() {
        return indexIsland;
    }






}
