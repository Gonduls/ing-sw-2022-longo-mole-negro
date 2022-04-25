package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.VC_GameEvent;

abstract class GameState {
    RoundController context;

    int numberOfEvents;


    GameState(RoundController context, int numberOfEvents){
        this.context=context;
        this.numberOfEvents=numberOfEvents;
    }

    public abstract boolean checkValidEvent(VC_GameEvent event);

}
