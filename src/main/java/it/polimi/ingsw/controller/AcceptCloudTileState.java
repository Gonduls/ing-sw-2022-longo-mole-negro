package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.events.ActivateCharacterCardEvent;
import it.polimi.ingsw.messages.events.ChooseCloudTileEvent;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Player;

/**
 * Third step in Action Phase
 *
 */
public class AcceptCloudTileState extends  GameState {
    AcceptCloudTileState(RoundController context, int numberOfEvents) {
        super(context, numberOfEvents);
    }

    @Override
    public boolean checkValidEvent(GameEvent event) {
        return event.getEventType() == GameEventType.CHOOSE_CLOUD_TILE ||
                event.getEventType() == GameEventType.ACTIVATE_CHARACTER_CARD;
    }

    @Override
    public void executeEvent(GameEvent event) throws Exception {
        switch (event.getEventType()) {
            case CHOOSE_CLOUD_TILE: {
                ChooseCloudTileEvent eventCast = (ChooseCloudTileEvent) event;
                //Player player = context.getPlayerByUsername(eventCast.getPlayerName());
                Player player = context.getSeatedPlayers()[eventCast.getPlayerNumber()];
                int cloudIndex = eventCast.getCloudIndex();

                    context.gameManager.emptyCloudInPlayer(cloudIndex, player);
                    numberOfEvents--;


                if (numberOfEvents == 0) {
                    if (context.getPlayingOrderIndex() == context.getNumberOfPlayers() - 1) {
                        //new round
                        context.setPlayingOrderIndex(0);

                        context.gameManager.getModelObserver().changeTurn(context.getCurrentPlayer().getPlayerNumber());

                        context.changeState(new AcceptAssistantCardState(context, context.getNumberOfPlayers()));

                        context.gameManager.getModelObserver().changePhase(GamePhase.PLANNING_PHASE);



                    } else {
                        //new turn for the new player
                        context.setPlayingOrderIndex(context.getPlayingOrderIndex() + 1);
                        context.gameManager.getModelObserver().changeTurn(context.getCurrentPlayer().getPlayerNumber());

                        context.gameManager.setUsedCard(-1, context.getCurrentPlayer().getPlayerNumber());

                        for(CharacterCard cc: context.gameManager.getActiveCards()){
                            cc.deactivateEffect();
                        }

                        context.changeState(new AcceptMoveStudentFromEntranceState(context, 3));
                        context.gameManager.getModelObserver().changePhase(GamePhase.ACTION_PHASE_ONE);

                    }

                }

            break;
            }

            case ACTIVATE_CHARACTER_CARD:{
                if (!context.isExpertMode()) {
                   throw new Exception("You can't  use character card in easy mode");
                }
                ActivateCharacterCardEvent eventCast = (ActivateCharacterCardEvent) event;

                int cardId = eventCast.getCardId();

                if (!context.gameManager.isCardActive(eventCast.getCardId())){
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

                break;

            }
        }

    }

}
