package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.events.viewcontroller.ChooseColorEvent;
import it.polimi.ingsw.messages.events.viewcontroller.GameEventType;
import it.polimi.ingsw.messages.events.viewcontroller.VC_GameEvent;
import it.polimi.ingsw.model.CharacterCard;
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
    public boolean checkValidEvent(VC_GameEvent event) {
        return event.getEventType()== GameEventType.CHOOSE_COLOR;
    }


    public void executeEvent(VC_GameEvent event){
        ChooseColorEvent eventCast = (ChooseColorEvent) event;
        cc.setColor(eventCast.getColor());
        numberOfEvents--;
        context.changeState(nextState);
    }
}
