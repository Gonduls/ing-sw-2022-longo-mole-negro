package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.messages.events.viewcontroller.ChooseIslandEvent;
import it.polimi.ingsw.messages.events.viewcontroller.GameEventType;
import it.polimi.ingsw.messages.events.viewcontroller.VC_GameEvent;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.CharacterCardFive;


/**
 *metti 4 noEntry sulla carta. Piazza una tessera su un’isola a tua scelta
 *NumberOfEvents should be 1
 *
 */

public class CharacterFiveState extends  CharacterState{


    CharacterCardFive cc;
    GameState nextState;

    public CharacterFiveState(RoundController context, int numberOfEvents, GameState nextState, CharacterCardFive cc) {
        super(context, numberOfEvents);
        this.cc = cc;
        this.nextState=nextState;
    }


    @Override
    public boolean checkValidEvent(VC_GameEvent event) {
        return event.getEventType()== GameEventType.CHOOSE_ISLAND;
    }

    @Override
    public void executeEvent(VC_GameEvent event) throws NoSuchStudentException {


        if (cc.getNoEntryToken() ==0) {
           //todo -> this was a stupid move player
        } else {
            ChooseIslandEvent eventCast = (ChooseIslandEvent) event;
            context.gameManager.addNoEntry(eventCast.getIslandIndex());
            numberOfEvents--;
            cc.removeNoEntryToken();
        }


        if(numberOfEvents == 0){
            context.changeState(nextState);
        }

    }
}
