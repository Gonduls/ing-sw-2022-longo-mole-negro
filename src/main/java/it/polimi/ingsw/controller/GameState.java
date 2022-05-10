package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.messages.events.viewcontroller.VC_GameEvent;

public abstract class GameState {
    RoundController context;

    int numberOfEvents;


    GameState(RoundController context, int numberOfEvents){
        this.context=context;
        this.numberOfEvents=numberOfEvents;
    }

    public abstract boolean checkValidEvent(VC_GameEvent event);

    public abstract void executeEvent(VC_GameEvent event)  throws NoSuchStudentException, Exception;

}
