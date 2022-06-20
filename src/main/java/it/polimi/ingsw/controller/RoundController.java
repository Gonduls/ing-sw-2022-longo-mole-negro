package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.ChangePhase;
import it.polimi.ingsw.messages.ChangeTurn;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.messages.StartGame;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.ModelObserver;
import it.polimi.ingsw.server.Room;

import java.util.*;

public class RoundController {


    //the order in which the players are "seated"
    private Player[] seatedPlayers;
    // in the first round is random, in the next rounds it qw

    //it's the order in which the players will do their turns.
    //it's established after playing the assistants cards
    private ArrayList<Player> playingOrder;

    private int playingOrderIndex;

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
        this.room = room;

        room.sendBroadcast(new StartGame(expertMode));

        int playerToAdd = new Random().nextInt(playersNames.length);



        for(int i=playerToAdd; playingOrder.size()<playersNames.length; i= (i+1)%playersNames.length){
            this.seatedPlayers[i] = new Player(i, playersNames[i], playersNames.length == 3);
                playingOrder.add(seatedPlayers[i]);
        }
        playingOrderIndex=0;
        gameManager= new GameManager(this.seatedPlayers, expertMode,new ModelObserver(room));
        gameState = new AcceptAssistantCardState(this, seatedPlayers.length);


        room.sendBroadcast(new ChangeTurn(playingOrder.get(playingOrderIndex).getPlayerNumber()));
        room.sendBroadcast(new ChangePhase(GamePhase.PLANNING_PHASE));
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


        if(playingOrder.get(playingOrderIndex).getPlayerNumber() != event.getPlayerNumber()){
            throw new Exception("It's not your turn");
        }


       if(event.getEventType() == GameEventType.ACTIVATE_CHARACTER_CARD && !isExpertMode()){
           throw new Exception("you cannot activate character cards in simple mode");
       }
        if (gameState.checkValidEvent(event)) {

            gameState.executeEvent(event);

        } else {
            throw new Exception("this move is not allowed at this stage of the game");
        }
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


    void handleCard( GameState currentState , int cardId) throws Exception {
        if (!gameManager.isCardActive(cardId)){
            throw new Exception("This card is not present in the game");
        }

        if (gameManager.getUsedCard() != -1){
            throw new Exception("You already activated a card");
        }

        if (getCurrentPlayer().getCoinsOwned() < gameManager.findCardById(cardId).getPrice()){
            throw new Exception("You don't have enough coins");
        }


        gameManager.setUsedCard(cardId, getCurrentPlayer().getPlayerNumber());
       if(cardId == 6) { // this is a bit junky, but it's the only card that needs this kind of information
           CharacterCardSix cc6 = (CharacterCardSix) gameManager.findCardById(6);
           cc6.setPlayer(getCurrentPlayer());
       }
        //this could be put into setUsedCard
        getCurrentPlayer().removeCoins(gameManager.findCardById(cardId).getPrice());
        //this could be put inside setUsedCard
        gameManager.findCardById(cardId).increasePrice();
        if(gameManager.findCardById(cardId).getCharacterState(this, currentState) != null){
            changeState(gameManager.findCardById(cardId).getCharacterState(this, currentState));
        }


    }




}