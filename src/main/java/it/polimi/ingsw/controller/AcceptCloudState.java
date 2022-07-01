package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.messages.events.ActivateCharacterCardEvent;
import it.polimi.ingsw.messages.events.ChooseCloudEvent;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Player;

/**
 * Third step in Action Phase.
 * The only allowed moves are choosing a cloud and activating a character card.
 *
 */
public class AcceptCloudState extends  GameState {
    AcceptCloudState(RoundController context, int numberOfEvents) {
        super(context, numberOfEvents);
    }


    /**
     * @param event The event to check
     * @return true if the event is a CHOOSE_CLOUD or ACTIVATE_CHARACTER_CARD
     */
    @Override
    public boolean checkValidEvent(GameEvent event) {
        return event.getEventType() == GameEventType.CHOOSE_CLOUD ||
                event.getEventType() == GameEventType.ACTIVATE_CHARACTER_CARD;
    }

    /**
     *  It empties the chosen cloud content to the current player.
     *  After that it either, moves to the planning phase or to the action phase 1 (move student from entrance).
     * @param event The event to process.
     * @throws GameException If something went wrong in the process of emptying the cloud to the player.
     */
    @Override
    public void executeEvent(GameEvent event) throws GameException {
        GameEventType eventType = event.getEventType();
        if (eventType == GameEventType.CHOOSE_CLOUD) {
            ChooseCloudEvent eventCast = (ChooseCloudEvent) event;
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

                    for (CharacterCard cc : context.gameManager.getActiveCards()) {
                        cc.deactivateEffect();
                    }

                    context.changeState(new AcceptMoveStudentFromEntranceState(context, context.getNumberOfPlayers() == 3 ? 4 : 3));
                    context.gameManager.getModelObserver().changePhase(GamePhase.ACTION_PHASE_ONE);

                }

            }
        } else if (eventType == GameEventType.ACTIVATE_CHARACTER_CARD) {
            ActivateCharacterCardEvent eventCast = (ActivateCharacterCardEvent) event;

            int cardId = eventCast.getCardId();

            context.handleCard(this, cardId);
        }
    }
}