package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.messages.events.ChooseIslandEvent;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.messages.GameEvent;
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
    public boolean checkValidEvent(GameEvent event) {
        return event.getEventType()== GameEventType.CHOOSE_ISLAND;
    }

    @Override
    public void executeEvent(GameEvent event) throws NoSuchStudentException {


        if (cc.getNoEntryToken() > 0) {
            ChooseIslandEvent eventCast = (ChooseIslandEvent) event;
            context.gameManager.addNoEntry(eventCast.getIslandIndex());
            numberOfEvents--;
            cc.removeNoEntryToken();
        } // if the player activate the card with 0 token left, that's his fault, not mine.


        if(numberOfEvents == 0){
            context.changeState(nextState);
        }

    }
}
