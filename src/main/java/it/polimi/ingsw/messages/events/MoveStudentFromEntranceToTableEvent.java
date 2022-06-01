package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.Color;

public class MoveStudentFromEntranceToTableEvent extends GameEvent {

    String playerName;
    Color color;

    int playerNumber;

    public MoveStudentFromEntranceToTableEvent(Color color, int playerNumber) {
        this.color = color;
        this.playerNumber = playerNumber;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.MOVE_STUDENT_FROM_ENTRANCE_TO_TABLE;
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
}
