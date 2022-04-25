package it.polimi.ingsw.controller;

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


    public void changeState(GameState newGameState){
        this.gameState=newGameState;
    }








}
