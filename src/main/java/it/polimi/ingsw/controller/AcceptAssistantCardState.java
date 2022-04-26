package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.GameEventType;
import it.polimi.ingsw.events.VC_GameEvent;

public class AcceptAssistantCardState extends GameState {


    AcceptAssistantCardState(RoundController context, int numberOfEvents) {
        super(context, numberOfEvents);
    }

    @Override
    public boolean checkValidEvent(VC_GameEvent event) {
        if (event.getEventType() == GameEventType.PLAY_ASSISTANT_CARD) {
            numberOfEvents--;
            if (numberOfEvents==0) {context.changeState(new AcceptMoveStudentFromEntranceState(context, 3));}
            return true;


        }
        return false;

    }




}
