package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.Color;

public class SwapStudentEntranceTableEvent extends GameEvent {


    String playerName;



    Color studentFromEntrance;
    Color studentFromTable;



    @Override
    public GameEventType getEventType() {
        return GameEventType.SWAP_STUDENT_ENTRANCE_TABLE;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }


    public Color getStudentFromEntrance() {
        return studentFromEntrance;
    }

    public Color getStudentFromTable() {
        return studentFromTable;
    }
}
