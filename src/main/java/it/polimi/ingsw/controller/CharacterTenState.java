package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.events.ChooseColorEvent;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.CharacterCardTen;

public class CharacterTenState extends CharacterState{
    GameState nextState;
    CharacterCardTen cc;

    public CharacterTenState(RoundController context, int numberOfEvents, GameState nextState, CharacterCardTen cc) {
        super(context, numberOfEvents);
        this.nextState=nextState;
        this.cc = cc;
    }


    @Override
    public boolean checkValidEvent(GameEvent event) {
        return event.getEventType()== GameEventType.CHOOSE_COLOR;
    }


    public void executeEvent(GameEvent event){
        ChooseColorEvent eventCast = (ChooseColorEvent) event;
        cc.setColor(eventCast.getColor());
        numberOfEvents--;
        context.changeState(nextState);
    }
}
