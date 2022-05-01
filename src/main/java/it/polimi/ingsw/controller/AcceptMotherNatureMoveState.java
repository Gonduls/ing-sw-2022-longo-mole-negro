package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.viewcontroller.GameEventType;
import it.polimi.ingsw.events.viewcontroller.MoveMotherNatureEvent;
import it.polimi.ingsw.events.viewcontroller.VC_GameEvent;
import it.polimi.ingsw.model.Player;

/**
 * Second step in Action Phase
 *
 */
public class AcceptMotherNatureMoveState extends GameState {
    AcceptMotherNatureMoveState(RoundController context, int numberOfEvents) {
        super(context, numberOfEvents);
    }

    @Override
    public boolean checkValidEvent(VC_GameEvent event) {

        return event.getEventType() == GameEventType.MOVE_MOTHER_NATURE ||
                event.getEventType() == GameEventType.ACTIVATE_CHARACTER_CARD;
    }


    @Override
    public void executeEvent(VC_GameEvent event) {

        switch(event.getEventType()){
            case MOVE_MOTHER_NATURE: {
                MoveMotherNatureEvent eventCast = (MoveMotherNatureEvent) event;

                Player player = context.getPlayerByUsername(eventCast.getPlayerName());
                int amount = eventCast.getAmount();

                try{
                    context.gameManager.moveMotherNature(amount);
                } catch (IllegalArgumentException e) {
                    //todo send an error back
                }

                break;
            }

            case ACTIVATE_CHARACTER_CARD:{
                //todo eheheheh
            }


        }
    }
}
