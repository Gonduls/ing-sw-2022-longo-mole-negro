package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.messages.events.ActivateCharacterCardEvent;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.messages.events.MoveMotherNatureEvent;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.Player;

/**
 * Second step in Action Phase.
 * The only allowed moves are moving mother nature and activating a character card.
 *
 */
public class AcceptMotherNatureMoveState extends GameState {
    AcceptMotherNatureMoveState(RoundController context, int numberOfEvents) {
        super(context, numberOfEvents);
    }


    /**
     * @param event The event to check
     * @return true if the event is a MOVE_MOTHER_NATURE or ACTIVATE_CHARACTER_CARD
     */
    @Override
    public boolean checkValidEvent(GameEvent event) {

        return event.getEventType() == GameEventType.MOVE_MOTHER_NATURE ||
                event.getEventType() == GameEventType.ACTIVATE_CHARACTER_CARD;
    }

    /**
     *
     * It moves mother nature by the amount specified in the event.
     * When done it moves to the "Choose Cloud" phase.
     * @param event The event to process.
     * @throws GameException If Mother Nature was moved in an illegal way
     */
    @Override
    public void executeEvent(GameEvent event) throws GameException {

        GameEventType eventType = event.getEventType();
        if (eventType == GameEventType.MOVE_MOTHER_NATURE) {
            MoveMotherNatureEvent eventCast = (MoveMotherNatureEvent) event;

            Player player = context.getSeatedPlayers()[eventCast.getPlayerNumber()];
            int amount = eventCast.getAmount();

            if (context.gameManager.getUsedCard() == 1) {
                if (amount > context.getPlayerMaxSteps().get(player).getSteps() + 2) {
                    throw new GameException("you cannot move mother nature more than " + (context.getPlayerMaxSteps().get(player).getSteps() + 2));
                }
            } else {
                if (amount > context.getPlayerMaxSteps().get(player).getSteps()) {
                    throw new GameException("you cannot move mother nature more than " + context.getPlayerMaxSteps().get(player).getSteps());
                }
            }

            try {
                context.gameManager.moveMotherNature(amount);
            } catch (IllegalArgumentException e) {
                throw new GameException("this should be impossible, we already check the legality of the move");
            }

            numberOfEvents--;
            if (numberOfEvents == 0) {
                context.changeState(new AcceptCloudState(context, 1));
                context.gameManager.getModelObserver().changePhase(GamePhase.ACTION_PHASE_THREE);
            }
        } else if (eventType == GameEventType.ACTIVATE_CHARACTER_CARD) {
            ActivateCharacterCardEvent eventCast = (ActivateCharacterCardEvent) event;
            int cardId = eventCast.getCardId();
            context.handleCard(this, cardId);
        }
    }
}
