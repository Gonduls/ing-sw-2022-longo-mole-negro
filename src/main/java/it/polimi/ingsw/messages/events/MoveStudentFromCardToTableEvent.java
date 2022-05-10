package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.Color;

public class MoveStudentFromCardToTableEvent extends GameEvent {

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
