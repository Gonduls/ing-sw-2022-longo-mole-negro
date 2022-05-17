package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.events.ActivateCharacterCard;
import it.polimi.ingsw.messages.events.ChooseCloudTileEvent;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
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
                Player player = context.getPlayerByUsername(eventCast.getPlayerName());
                int cloudIndex = eventCast.getCloudIndex();

                try {
                    context.gameManager.emptyCloudInPlayer(cloudIndex, player);
                    numberOfEvents--;
                } catch (NoSpaceForStudentException | NoSuchStudentException e) {
                    //todo:  non dovrebbe essere possibile
                }

                if (numberOfEvents == 0) {
                    if (context.getPlayingOrderIndex() == context.getNumberOfPlayers() - 1) {
                        //new round
                        context.setPlayingOrderIndex(0);
                        context.changeState(new AcceptAssistantCardState(context, context.getNumberOfPlayers()));
                    } else {
                        //new turn for the new player
                        context.setPlayingOrderIndex(context.getPlayingOrderIndex() + 1);
                        context.gameManager.setUsedCard(-1);

                        for(CharacterCard cc: context.gameManager.getActiveCards()){
                            cc.deactivateEffect();
                        }

                        context.changeState(new AcceptMoveStudentFromEntranceState(context, 3));
                    }

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
