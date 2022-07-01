package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.messages.EndGame;
import it.polimi.ingsw.messages.events.ActivateCharacterCardEvent;
import it.polimi.ingsw.messages.events.ChooseCloudEvent;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Color;
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

    String[] endConditionAssistantOrBag(){

        List<String> winnerNames = new ArrayList<>();
        Player winner = null;
        HashMap <Player, Integer> numberOfPoints= new HashMap<>();
        for (Player p: context.gameManager.getPlayers()){
           numberOfPoints.put(p, p.getTowersLeft());
        }

        int min = Integer.MAX_VALUE;
        for (int i : numberOfPoints.values()){
            if (i<min) min = i;
        }


        for (Player p: numberOfPoints.keySet()) {
            if (numberOfPoints.get(p) > min) {
                numberOfPoints.remove(p);
            }
        }

        for(Color c: context.gameManager.getProfessors().getOwners().keySet()){
            numberOfPoints.computeIfPresent(context.gameManager.getProfessors().getOwners().get(c), (k,v) ->v+1);
        }

        int  max = numberOfPoints.values().stream().max(Integer::compareTo).get();

        for (Player p: numberOfPoints.keySet()) {
            if (numberOfPoints.get(p) != max) {
                numberOfPoints.remove(p);
            }
        }

         winner = numberOfPoints.keySet().stream().findFirst().get();

        if (winner != null)  {
            winnerNames.add(winner.getUsername());
            if(context.getNumberOfPlayers() == 4 ){
            winnerNames.add(context.getSeatedPlayers()[(winner.getPlayerNumber()+2)%4].getUsername());
            }
            return  winnerNames.toArray(String[]::new);
        }
        else {
            return new String[0];
        }





    }
}