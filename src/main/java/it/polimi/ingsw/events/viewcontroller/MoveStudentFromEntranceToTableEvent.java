package it.polimi.ingsw.events.viewcontroller;

import it.polimi.ingsw.model.Color;

public class MoveStudentFromEntranceToTableEvent implements VC_GameEvent {

    String playerName;
    Color color;

    @Override
    public GameEventType getEventType() {
        return GameEventType.MOVE_STUDENT_FROM_ENTRANCE_TO_TABLE;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    public Color getColor(){
        return color;
    }
}
