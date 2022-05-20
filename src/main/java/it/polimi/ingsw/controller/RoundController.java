package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.Room;

import java.util.*;

public class RoundController {


    //the order in which the players are "seated"
    private Player[] seatedPlayers;
    // in the first round is random, in the next rounds it qw
    private int firstPlayerToPlayCard;

    //it's the order in which the players will do their turns.
    //it's established after playing the assistants cards
    private ArrayList<Player> playingOrder;

    private int playingOrderIndex;

    private int[] maxSteps;


    // it's the index of a player in seatedPlayers
    private int currentPlayingPlayer;



    private final boolean expertMode;

    public boolean isExpertMode() {
        return expertMode;
    }


    GameManager gameManager;

    GameState gameState;

    Map<Player, AssistantCard> playerMaxSteps;

    Room room;


    public RoundController(String[] playersNames, boolean expertMode, Room room) {
        this.playingOrder = new ArrayList<>();
        this.expertMode = expertMode;
        this.seatedPlayers = new Player[playersNames.length];

        for(int i=0; i<playersNames.length; i++){
            this.seatedPlayers[i] = new Player(i, playersNames[i], playersNames.length == 3);
            if(new Random().nextBoolean()){
                playingOrder.add(seatedPlayers[i]);
            } else {
                playingOrder.add(0,seatedPlayers[i]);
            }
        }
        playingOrderIndex=0;
        gameManager= new GameManager(this.seatedPlayers, expertMode);
        gameState = new AcceptAssistantCardState(this, seatedPlayers.length);

        //
        //currentPlayingPlayer = new Random().nextInt(seatedPlayers.length);
        this.room = room;

    }

    void changeState(GameState newGameState) {
        this.gameState = newGameState;
    }

    public Player[] getSeatedPlayers() {
        return seatedPlayers;
    }

    /**
     * This is access point for the view.
     * The virtual View will send events to the controller via this method
     *
     * @param event
     */

    public void handleEvent(GameEvent event) throws Exception {


        if(!Objects.equals(event.getPlayerName(), playingOrder.get(playingOrderIndex).getUsername())){
            throw new Exception("It's not your turn");
        }

       /*
       if (event.getPlayerName()!= seatedPlayers[getCurrentPlayingPlayer()].getUsername()){
            return;
        }
        */
       if(event.getEventType() == GameEventType.ACTIVATE_CHARACTER_CARD && isExpertMode()==false){
           throw new Exception("you cannot activate character cards in simple mode");
       }
        if (gameState.checkValidEvent(event)) {

            gameState.executeEvent(event);

        } else {
            throw new Exception("this move is not allowed at this stage of the game");
        }

        firstPlayerToPlayCard = 0;
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

    public ArrayList<Player> getPlayingOrder() {
        return playingOrder;
    }

    public void setPlayingOrder(ArrayList<Player> playingOrder) {
        this.playingOrder = playingOrder;
    }



    public Map<Player, AssistantCard> getPlayerMaxSteps() {
        return playerMaxSteps;
    }

    public void resetPlayerMaxSteps(){
        this.playerMaxSteps = new HashMap<>();
    }


    public Player getCurrentPlayer(){
        return playingOrder.get(playingOrderIndex);
    }
}