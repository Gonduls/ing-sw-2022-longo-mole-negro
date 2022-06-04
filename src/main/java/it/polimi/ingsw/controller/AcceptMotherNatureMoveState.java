package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.events.ActivateCharacterCardEvent;
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

                Player player = context.getSeatedPlayers()[eventCast.getPlayerNumber()];
                int amount = eventCast.getAmount();

               if (context.gameManager.getUsedCard() == 1) {
                   if (amount > context.getPlayerMaxSteps().get(player).getSteps() +2 ) {
                       throw new Exception("you cannot move mother nature more than " + (context.getPlayerMaxSteps().get(player).getSteps() + 2 ));
                   }
               } else {
                   if (amount > context.getPlayerMaxSteps().get(player).getSteps()) {
                       throw new Exception("you cannot move mother nature more than " + context.getPlayerMaxSteps().get(player).getSteps());
                   }
               }

               try{
                    context.gameManager.moveMotherNature(amount);
                } catch (IllegalArgumentException e) {
                    //todo send an error back
                }

                numberOfEvents--;
                if(numberOfEvents == 0){
                    context.changeState(new AcceptCloudTileState(context,1));
                    context.gameManager.getModelObserver().changePhase(GamePhase.ACTION_PHASE_THREE);
                }

                break;
            }

            case ACTIVATE_CHARACTER_CARD:{

                if (!context.isExpertMode()) {
                    throw new Exception("You can't  use character card in easy mode");
                }

                ActivateCharacterCardEvent eventCast = (ActivateCharacterCardEvent) event;

                int cardId = eventCast.getCardId();

                context.handleCard(this,cardId);
              /*  if (!context.gameManager.isCardActive(eventCast.getCardId())){
                    throw new Exception("This card is not present in the game");
                }

                if (context.gameManager.getUsedCard() != -1){
                    throw new Exception("You already activated a card");
                }

                if (context.getCurrentPlayer().getCoinsOwned() < context.gameManager.findCardById(eventCast.getCardId()).getPrice()){
                    throw new Exception("You don't have enough coins");
                }

                context.gameManager.setUsedCard(cardId,context.getCurrentPlayer().getPlayerNumber());

                context.getCurrentPlayer().removeCoins(context.gameManager.findCardById(cardId).getPrice());

                context.gameManager.findCardById(cardId).increasePrice();




                if (context.gameManager.findCardById(cardId).getCharacterState(context, this) != null ) {
                    context.changeState(context.gameManager.findCardById(cardId).getCharacterState(context, this));
                }


               */

                break;
            }


        }
    }
}
