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
                Player player = context.getSeatedPlayers()[eventCast.getPlayerNumber()];
                int cloudIndex = eventCast.getCloudIndex();

                    context.gameManager.emptyCloudInPlayer(cloudIndex, player);
                    numberOfEvents--;


                if (numberOfEvents == 0) {
                    context.gameManager.setUsedCard(-1, context.getCurrentPlayer().getPlayerNumber());

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

                        for(CharacterCard cc: context.gameManager.getActiveCards()){
                            cc.deactivateEffect();
                        }

                        context.changeState(new AcceptMoveStudentFromEntranceState(context, context.getNumberOfPlayers()==3?4:3));
                        context.gameManager.getModelObserver().changePhase(GamePhase.ACTION_PHASE_ONE);

                    }

                }

            break;
            }

            case ACTIVATE_CHARACTER_CARD:{
                ActivateCharacterCardEvent eventCast = (ActivateCharacterCardEvent) event;

                int cardId = eventCast.getCardId();

                context.handleCard(this,cardId);
                break;

            }
        }

    }

}
