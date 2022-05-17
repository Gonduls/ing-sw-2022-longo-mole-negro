package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.events.ActivateCharacterCard;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.messages.events.MoveMotherNatureEvent;
import it.polimi.ingsw.messages.GameEvent;
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
    public boolean checkValidEvent(GameEvent event) {

        return event.getEventType() == GameEventType.MOVE_MOTHER_NATURE ||
                event.getEventType() == GameEventType.ACTIVATE_CHARACTER_CARD;
    }


    @Override
    public void executeEvent(GameEvent event) throws Exception {

        switch(event.getEventType()){
            case MOVE_MOTHER_NATURE: {
                MoveMotherNatureEvent eventCast = (MoveMotherNatureEvent) event;

                Player player = context.getPlayerByUsername(eventCast.getPlayerName());
                int amount = eventCast.getAmount();

               //todo check that is a legal move
                try{
                    context.gameManager.moveMotherNature(amount);
                } catch (IllegalArgumentException e) {
                    //todo send an error back
                }

                numberOfEvents--;
                if(numberOfEvents == 0){
                    context.changeState(new AcceptCloudTileState(context,1));
                }

                break;
            }

            case ACTIVATE_CHARACTER_CARD:{

                if (!context.isExpertMode()) {
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
