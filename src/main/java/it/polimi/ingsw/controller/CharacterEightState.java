package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.messages.events.ChooseIslandEvent;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.messages.GameEvent;

/**
 *
 *
 */
public class CharacterEightState extends CharacterState{
    GameState nextState;


    public CharacterEightState(RoundController context, int numberOfEvents, GameState nextState) {
        super(context, numberOfEvents);
        this.nextState=nextState;
    }

    @Override
    public boolean checkValidEvent(GameEvent event) {
        return event.getEventType()== GameEventType.CHOOSE_ISLAND;
    }


    @Override
    public void executeEvent(GameEvent event) throws NoSuchStudentException {
        ChooseIslandEvent eventCast = (ChooseIslandEvent) event;

        if (eventCast.getIslandIndex() <0 || eventCast.getIslandIndex()>=context.gameManager.getIslands().size() ){
            throw new IllegalArgumentException("this island doesn't exist");
        }

        context.gameManager.calculateInfluenceWithoutMovement(eventCast.getIslandIndex());
        numberOfEvents--;

        if(numberOfEvents ==0) {
            context.changeState(nextState);
        }

    }
}
