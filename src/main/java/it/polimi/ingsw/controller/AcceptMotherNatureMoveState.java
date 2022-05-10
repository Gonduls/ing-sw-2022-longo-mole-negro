package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.events.viewcontroller.ActivateCharacterCard;
import it.polimi.ingsw.messages.events.viewcontroller.GameEventType;
import it.polimi.ingsw.messages.events.viewcontroller.MoveMotherNatureEvent;
import it.polimi.ingsw.messages.events.viewcontroller.VC_GameEvent;
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
    public void executeEvent(VC_GameEvent event) throws Exception {

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

                if (!context.isHardMode()) {
                    //todo send a nack
                }

                ActivateCharacterCard eventCast = (ActivateCharacterCard) event;

                int cardId = eventCast.getCardId();

                if (!context.gameManager.isCardActive(eventCast.getCardId())){
                    throw new Exception("This card is not present in the game");
                }

                if (context.gameManager.getUsedCard() != -1){
                    throw new Exception("You already activated a card");
                }

                context.gameManager.setUsedCard(cardId);

                if (context.gameManager.findCardById(cardId).getCharacterState(context, this) != null ) {
                    context.changeState(context.gameManager.findCardById(cardId).getCharacterState(context, this));
                }

                break;
            }


        }
    }
}
