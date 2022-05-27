package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.messages.events.PlayAssistantCardEvent;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Player;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Planning Phase.
 * The first step is handled in the constructor.
 * The second one is handled by the PLAY_ASSISTANT_CARD event.
 */
public class AcceptAssistantCardState extends GameState {



    AssistantCard[] cardsPlayedThisTurn = new AssistantCard[context.getNumberOfPlayers()];
    List<Player> newPlayingOrder;


    AcceptAssistantCardState(RoundController context, int numberOfEvents) {
        super(context, numberOfEvents);
        context.gameManager.refillClouds();//this is the first step of the planning phase
        newPlayingOrder = new ArrayList<>();
        context.resetPlayerMaxSteps();
    }

    @Override
    public boolean checkValidEvent(GameEvent event) {
        return event.getEventType() == GameEventType.PLAY_ASSISTANT_CARD;
    }

    @Override
    public void executeEvent(GameEvent event) throws Exception {

        PlayAssistantCardEvent eventCast = (PlayAssistantCardEvent) event;

        AssistantCard cardPlayed = eventCast.getAssistantCard();

        System.out.println(eventCast.getPlayerName());
        Player player = context.getPlayerByUsername(eventCast.getPlayerName());


        if (player.getCardsLeft().contains(cardPlayed)){
            for(int i =0 ; i< context.getNumberOfPlayers(); i++){

                if(cardsPlayedThisTurn[i] == null) continue;

                if(cardsPlayedThisTurn[i].getValue() == cardPlayed.getValue()){
                    //checks that the current player has only cards that are in cardsPlayedThisTurn
                    for(AssistantCard ac : player.getCardsLeft()){
                        if(ac.getValue() != cardsPlayedThisTurn[i].getValue())
                            throw new Exception("You can't play this Assistant Card.");
                    }
                }
            }

            player.pickCard(cardPlayed);
        }

        cardsPlayedThisTurn[context.getNumberOfPlayers()- numberOfEvents ] = cardPlayed;

        //this sets the playing order
        if(newPlayingOrder.isEmpty()){
            newPlayingOrder.add(player);
        } else if (newPlayingOrder.size()==1) {

            if (cardsPlayedThisTurn[newPlayingOrder.get(0).getPlayerNumber()].getValue() < cardPlayed.getValue()) {
                newPlayingOrder.add(player);
            } else {
                newPlayingOrder.add(0, player);
            }

        }
        else {
            for(int i = 0; i < newPlayingOrder.size();i++){
                //this works on the hypothesis that the list is already ordered in ascending order
                if(cardPlayed.getValue() < cardsPlayedThisTurn[newPlayingOrder.get(i).getPlayerNumber()].getValue()){
                    newPlayingOrder.add(i, player);
                    break;
                }
                //in case we are inserting the  biggest element so far
                if(i== newPlayingOrder.size()-1){
                    newPlayingOrder.add(player);
                }
            }


        }

        context.getPlayerMaxSteps().put(player, cardPlayed);

        context.gameManager.getModelObserver().playAssistantCard(cardPlayed,player.getPlayerNumber());

        numberOfEvents--;

    if (numberOfEvents ==0){
            //shallow copy->this should help with the garbage collection
            context.setPlayingOrder(new ArrayList<>(newPlayingOrder));

            //change state to moveStudentFromEntrance
            context.setPlayingOrderIndex(0);

            context.gameManager.getModelObserver().changeTurn(context.getCurrentPlayer().getPlayerNumber());

            context.changeState(new AcceptMoveStudentFromEntranceState(context, 3));

            context.gameManager.getModelObserver().changePhase(GamePhase.ACTION_PHASE_ONE);
        }
        else {
            //next player
            context.setPlayingOrderIndex(context.getPlayingOrderIndex()+1);

            context.gameManager.getModelObserver().changeTurn(context.getCurrentPlayer().getPlayerNumber());
        }




    }

}
