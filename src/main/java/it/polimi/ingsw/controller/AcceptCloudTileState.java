package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.viewcontroller.ChooseCloudTileEvent;
import it.polimi.ingsw.events.viewcontroller.GameEventType;
import it.polimi.ingsw.events.viewcontroller.VC_GameEvent;
import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
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
    public boolean checkValidEvent(VC_GameEvent event) {
        return event.getEventType() == GameEventType.CHOOSE_CLOUD_TILE ||
                event.getEventType() == GameEventType.ACTIVATE_CHARACTER_CARD;
    }

    @Override
    public void executeEvent(VC_GameEvent event) {
        switch (event.getEventType()) {
            case CHOOSE_CLOUD_TILE: {
                ChooseCloudTileEvent
                        eventCast = (ChooseCloudTileEvent) event;
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
                        context.changeState(new AcceptMoveStudentFromEntranceState(context, 3));
                    }

                }


            }

            case ACTIVATE_CHARACTER_CARD:{
                //todo
            }
        }

    }

}
