package it.polimi.ingsw.messages.events.viewcontroller;

import it.polimi.ingsw.model.Color;

public class MoveStudentFromCardToTableEvent extends VC_GameEvent{

    String playerName;
    Color color;


    @Override
    public GameEventType getEventType() {
        return GameEventType.MOVE_STUDENT_FROM_CARD_TO_TABLE;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }


    public Color getColor() {
        return color;
    }
}
