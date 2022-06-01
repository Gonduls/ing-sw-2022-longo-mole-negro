package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.CharacterCard;

public abstract class CharacterState extends  GameState{




    CharacterState(RoundController context, int numberOfEvents) {
        super(context, numberOfEvents);
    }


    CharacterState(RoundController context, int numberOfEvents, GameState nextState, CharacterCard cc){
        super(context,numberOfEvents);



    }


    @Override
    public boolean checkValidEvent(GameEvent event) {
        return false;
    }

    @Override
    public void executeEvent(GameEvent event)  throws NoSuchStudentException,Exception {

    }
}
