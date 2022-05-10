package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.messages.events.viewcontroller.ChooseIslandEvent;
import it.polimi.ingsw.messages.events.viewcontroller.GameEventType;
import it.polimi.ingsw.messages.events.viewcontroller.VC_GameEvent;
import it.polimi.ingsw.model.CharacterCard;

public class CharacterEightState extends CharacterState{
    GameState nextState;

    CharacterEightState(RoundController context, int numberOfEvents) {
        super(context, numberOfEvents);
    }

    public CharacterEightState(RoundController context, int numberOfEvents, GameState nextState) {
        super(context, numberOfEvents);
        this.nextState=nextState;
    }

    @Override
    public boolean checkValidEvent(VC_GameEvent event) {
        return event.getEventType()== GameEventType.CHOOSE_ISLAND;
    }


    @Override
    public void executeEvent(VC_GameEvent event) throws NoSuchStudentException {
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
