package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.messages.GameEvent;


/**
 * This represents the state in which the controller is.
 * A Game State has a set of allowed moves.
 *  Each game state has a parameter called numberOfEvents which dictates the maximum number of "normal events" to process.
 *  Every move is a "normal event" except for:  activating a character card.
 *
 */
public abstract class GameState {
    RoundController context;

    int numberOfEvents;


    GameState(RoundController context, int numberOfEvents){
        this.context=context;
        this.numberOfEvents=numberOfEvents;
    }

    public abstract boolean checkValidEvent(GameEvent event);

    /**
     *  It applies the event(move) to the model.
     *  Each state implements only the logic that is necessary for all the moves allowed in that state.
     * @param event The event to process.
     * @throws GameException Thrown if the move was illegal, or there was a problem in the model.
     */
    public abstract void executeEvent(GameEvent event)  throws GameException;

}
