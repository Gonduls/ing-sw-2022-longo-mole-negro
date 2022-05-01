package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.viewcontroller.GameEventType;
import it.polimi.ingsw.events.viewcontroller.VC_GameEvent;
import it.polimi.ingsw.events.viewcontroller.PlayAssistantCardEvent;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Player;

import java.security.InvalidParameterException;

/**
 * Planning Phase.
 * The first step is handled in the constructor.
 * The second one is handled by the PLAY_ASSISTANT_CARD event.
 */
public class AcceptAssistantCardState extends GameState {



    AssistantCard[] cardsPlayedThisTurn = new AssistantCard[context.getNumberOfPlayers()];


    AcceptAssistantCardState(RoundController context, int numberOfEvents) {
        super(context, numberOfEvents);
        context.gameManager.refillClouds();//this is the first step of the planning phase
    }

    @Override
    public boolean checkValidEvent(VC_GameEvent event) {
        return event.getEventType() == GameEventType.PLAY_ASSISTANT_CARD;
    }

    @Override
    public void executeEvent(VC_GameEvent event) {

        if (!checkValidEvent( event)) return;
        PlayAssistantCardEvent eventCast = (PlayAssistantCardEvent) event;

        AssistantCard cardPlayed = eventCast.getAssistantCard();
        Player player = context.getPlayerByUsername(eventCast.getPlayerName());


        //checks that the player is the current playing player
        if(!player.getNickname().equals(context.getSeatedPlayers()[context.getCurrentPlayingPlayer()].getNickname())){
            return;
        }


        try {
            player.pickCard(cardPlayed); // check if it's possible to pick the card
        } catch(InvalidParameterException e){
        }



        cardsPlayedThisTurn[ context.getNumberOfPlayers()- numberOfEvents ] = cardPlayed;

        numberOfEvents--;


    if (numberOfEvents ==1){

            //set playing order

            //change state to moveStudentFromEntrance
            context.changeState(new AcceptMoveStudentFromEntranceState(context, 3));


        }
        else {


        }




    }

}
