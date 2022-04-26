package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.VC_GameEvent;
import it.polimi.ingsw.model.*;

public class RoundController {
    //the order in which the players are "seated"
    private Player[] seatedPlayers;
    // in the first round is random, in the next rounds it qw
    private int firstPlayerToPlayCard;

    //it's the order in which the players will do their turns.
    //it's established after playing the assistants cards
    private int[] playingOrder;

    private  int[] maxSteps;
    // it's the index of a player in seatedPlayers
    private  int currentPlayingPlayer;

    private boolean hardMode;

    GameManager gameManager;

    GameState gameState;

    public RoundController(){
        gameState = new AcceptAssistantCardState(this, 4);

    }

    void changeState(GameState newGameState){
        this.gameState=newGameState;
    }


    /**
     * This is access point for the view.
     * The virtual View will send events to the controller via this method
     * @param event
     */

    public void handleEvent(VC_GameEvent event){

        if(gameState.checkValidEvent(event)){
            //DO THING

        } else {
            //TODO notify that the event is wrong or inconsistent with the actual game state
        }

    }


















}
