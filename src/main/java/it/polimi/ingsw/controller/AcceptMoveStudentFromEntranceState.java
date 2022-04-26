package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.GameEventType;
import it.polimi.ingsw.events.VC_GameEvent;

public class AcceptMoveStudentFromEntranceState extends  GameState {


    AcceptMoveStudentFromEntranceState(RoundController context, int numberOfEvents) {
        super(context, numberOfEvents);
    }

    @Override
    public boolean checkValidEvent(VC_GameEvent event) {
        if(event.getEventType() == GameEventType.MOVE_STUDENT_FROM_ENTRANCE_TO_ISLAND){
            numberOfEvents--;
            return true;
        }

        if(event.getEventType() == GameEventType.MOVE_STUDENT_FROM_ENTRANCE_TO_DINING_ROOM){
            numberOfEvents--;
            return true;
        }

        if(event.getEventType() == GameEventType.ACTIVATE_CHARACTER_CARD){
            return true;
        }


        return false;
    }
}
