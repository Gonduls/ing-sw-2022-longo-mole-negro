package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.viewcontroller.VC_GameEvent;
import it.polimi.ingsw.model.*;

import java.util.Random;

public class RoundController {
    public Player[] getSeatedPlayers() {
        return seatedPlayers;
    }


    //the order in which the players are "seated"
    private Player[] seatedPlayers;
    // in the first round is random, in the next rounds it qw
    private int firstPlayerToPlayCard;

    //it's the order in which the players will do their turns.
    //it's established after playing the assistants cards
    private int[] playingOrder;



    private int playingOrderIndex;

    private int[] maxSteps;


    // it's the index of a player in seatedPlayers
    private int currentPlayingPlayer;

    public void setCurrentPlayingPlayer(int currentPlayingPlayer) {
        this.currentPlayingPlayer = currentPlayingPlayer;
    }



    private boolean hardMode;

    public boolean isHardMode() {
        return hardMode;
    }


    GameManager gameManager;

    GameState gameState;

    public RoundController() {
        gameState = new AcceptAssistantCardState(this, seatedPlayers.length);
        currentPlayingPlayer = new Random().nextInt(seatedPlayers.length);
    }

    void changeState(GameState newGameState) {
        this.gameState = newGameState;
    }


    /**
     * This is access point for the view.
     * The virtual View will send events to the controller via this method
     *
     * @param event
     */

    public void handleEvent(VC_GameEvent event) {


        if (event.getPlayerName()!= seatedPlayers[getCurrentPlayingPlayer()].getUsername()){
            return;
        }

        if (gameState.checkValidEvent(event)) {

            gameState.executeEvent(event);

        } else {
            //TODO notify that the event is wrong or inconsistent with the actual game state
        }

        firstPlayerToPlayCard = 0;
    }



    void setPlayingOrder(int[] playingOrder) {
        this.playingOrder = playingOrder;
    }


    Player getPlayerByUsername(String username) {
        for (Player p : seatedPlayers) {
            if (p.getUsername().equals(username)) {
                return p;
            }
        }
        return null;
    }

    int getPlayerIndex(Player player) {

        for (int i = 0; i < seatedPlayers.length; i++) {
            if (seatedPlayers[i] == player) return i;
        }

        return -1;
    }

    int getNumberOfPlayers(){
        return seatedPlayers.length;
    }

    int getCurrentPlayingPlayer (){
        return currentPlayingPlayer;
    }


    public int getPlayingOrderIndex() {
        return playingOrderIndex;
    }

    public void setPlayingOrderIndex(int playingOrderIndex) {
        this.playingOrderIndex = playingOrderIndex;
    }


}