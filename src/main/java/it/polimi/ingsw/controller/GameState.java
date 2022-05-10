package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.messages.GameEvent;

public abstract class GameState {
    RoundController context;

    int numberOfEvents;


    GameState(RoundController context, int numberOfEvents){
        this.context=context;
        this.numberOfEvents=numberOfEvents;
    }

    public abstract boolean checkValidEvent(GameEvent event);

    public abstract void executeEvent(GameEvent event)  throws NoSuchStudentException, Exception;

}
