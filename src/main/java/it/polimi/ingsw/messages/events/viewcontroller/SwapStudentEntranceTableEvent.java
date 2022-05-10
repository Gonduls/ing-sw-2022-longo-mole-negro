package it.polimi.ingsw.messages.events.viewcontroller;

import it.polimi.ingsw.model.Color;

public class SwapStudentEntranceTableEvent extends VC_GameEvent{


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
