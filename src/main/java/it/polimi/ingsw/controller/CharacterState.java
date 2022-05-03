package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.events.viewcontroller.VC_GameEvent;

public abstract class CharacterState extends  GameState{


    CharacterState(RoundController context, int numberOfEvents) {
        super(context, numberOfEvents);
    }


    CharacterState(RoundController context, int numberOfEvents, GameState nextState){
        super(context,numberOfEvents);
        /*
        here if

         */
        context.changeState(nextState);
    }

    @Override
    public boolean checkValidEvent(VC_GameEvent event) {
        return false;
    }

    @Override
    public void executeEvent(VC_GameEvent event) {

    }
}
