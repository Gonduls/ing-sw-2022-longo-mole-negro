package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.messages.EndGame;
import it.polimi.ingsw.messages.events.ActivateCharacterCardEvent;
import it.polimi.ingsw.messages.events.ChooseCloudEvent;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.GameManager;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

                    if( context.getCurrentPlayer().getCardsLeft().isEmpty()) {
                        context.room.sendBroadcast(new EndGame(endConditionAssistantOrBag()) );
                    }

                    if (context.gameManager.getBag().getAllStudents().values().stream().reduce(0, Integer::sum) == 0){
                        context.room.sendBroadcast(new EndGame(endConditionAssistantOrBag()) );

                    }


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

    /**
     * It checks the end condition in the case the assistant card are finished or the bag is empty
     * @return an array of string containing the names of the winners.
     */
    String[] endConditionAssistantOrBag(){
        GameManager gm = context.gameManager;
        Player[] players = gm.getPlayers();
        Player[] winner = new Player[2];
        int t0 = players[0].getTowersLeft();
        int t1 = players[1].getTowersLeft();
        int t2 = 6;
        int p0 = gm.countProfessors(0);
        int p1 = gm.countProfessors(1);
        int p2 = 0;

        if(players.length == 3){
            t2 = players[2].getTowersLeft();
            p2 = gm.countProfessors(2);
        }

        if(players.length == 3){
            if(t0 < t1 && t0 < t2)
                winner[0] = players[0];
            else if(t1 < t0 && t1 < t2)
                winner[0] = players[1];
            else if(t2 < t0 && t2 < t1)
                winner[0] = players[2];
            else if(t0 > t1){
                winner[0] = players[1];
                if(p1 < p2)
                    winner[0] = players[2];
                else if (p1 == p2) {
                    winner[1] = players[2];
                }
            } else if (t1 > t0) {
                winner[0] = players[0];
                if(p0 < p2)
                    winner[0] = players[2];
                else if (p0 == p2) {
                    winner[1] = players[2];
                }
            } else if (t2 > t0){
                winner[0] = players[0];
                if(p0 < p1)
                    winner[0] = players[1];
                else if (p0 == p1) {
                    winner[1] = players[1];
                }
            } else //should check again for professors but everyone wins
                return gm.parseWinResult(players);

            return gm.parseWinResult(winner);
        }

        // two or four players
        if(t0 < t1){
            winner[0] = players[0];
        }else if(t0 > t1){
            winner[0] = players[1];
        } else if(p0 > p1){
            winner[0] = players[0];
        } else if(p1 > p0){
            winner[0] = players[1];
        } else
            return gm.parseWinResult(players);

        if(players.length == 4)
            winner[1] = players[winner[0].getPlayerNumber() + 2];
        return gm.parseWinResult(winner);
    }
}