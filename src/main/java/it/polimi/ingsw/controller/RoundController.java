package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.messages.ChangePhase;
import it.polimi.ingsw.messages.ChangeTurn;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.messages.StartGame;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.ModelObserver;
import it.polimi.ingsw.server.Room;

import java.util.*;


/**
 * The main class of the controller.
 * This class follows the "state" pattern.
 * The controller has a reference to a Game State that represent the possible actions that can be taken
 * in the game in a precise moment.
 *
 */

public class RoundController {


    //the order in which the players are "seated"
    private Player[] seatedPlayers;
    // in the first round is random, in the next rounds it qw

    //it's the order in which the players will do their turns.
    //it's established after playing the assistants cards
    private List<Player> playingOrder;

    private int playingOrderIndex;

    private final boolean expertMode;

    public boolean isExpertMode() {
        return expertMode;
    }


    GameManager gameManager;

    GameState gameState;

    Map<Player, AssistantCard> playerMaxSteps;

    Room room;

    /**
     * The main and only constructor of the round controller. It creates the gameManager, which is the representation
     * of the model. By doing so it also creates the players objects.
     * It also sets the initial order of the round  starts with a random player and then proceeds
     * to follow the order of the players based on their number.
     * @param playersNames The names of the players in this game.
     * @param expertMode The rule set used for this game
     * @param room The room in which the players are.
     */
    public RoundController(String[] playersNames, boolean expertMode, Room room) {
        this.playingOrder = new ArrayList<>();
        this.expertMode = expertMode;
        this.seatedPlayers = new Player[playersNames.length];
        this.room = room;

        room.sendBroadcast(new StartGame(expertMode));

        int playerToAddNumber = new Random().nextInt(playersNames.length);


        while(playingOrder.size()< playersNames.length){
            this.seatedPlayers[playerToAddNumber] = new Player(playerToAddNumber, playersNames[playerToAddNumber], playersNames.length == 3);
            playingOrder.add(seatedPlayers[playerToAddNumber]);
            playerToAddNumber = (playerToAddNumber +1) % playersNames.length;
        }



        playingOrderIndex=0;
        gameManager= new GameManager(this.seatedPlayers, expertMode,new ModelObserver(room));
        gameState = new AcceptAssistantCardState(this, seatedPlayers.length);


        room.sendBroadcast(new ChangeTurn(playingOrder.get(playingOrderIndex).getPlayerNumber()));
        room.sendBroadcast(new ChangePhase(GamePhase.PLANNING_PHASE));
    }

    /**
     * Changes the state of the Round Controller to a new One.
     *
     * @param newGameState the new state of the controller
     */
    void changeState(GameState newGameState) {
        this.gameState = newGameState;
    }

    /**
     *
     * @return An array of Players. The order is the order in which the players are "seated".
     */
    public Player[] getSeatedPlayers() {
        return seatedPlayers;
    }

    /**
     * This is access point of the controller.
     * All the events, i.e. moves made by the players, are handled by this method.
     * The actual handling of the event is done in the gameState.
     * @param event The event to process.
     */

    public void handleEvent(GameEvent event) throws Exception {


        if(playingOrder.get(playingOrderIndex).getPlayerNumber() != event.getPlayerNumber()){
            throw new GameException("It's not your turn");
        }


       if(event.getEventType() == GameEventType.ACTIVATE_CHARACTER_CARD && !isExpertMode()){
           throw new GameException("you cannot activate character cards in simple mode");
       }
        if (gameState.checkValidEvent(event)) {

            gameState.executeEvent(event);

        } else {
            throw new GameException("this move is not allowed at this stage of the game");
        }
    }


    /**
     * Returns the number of players in this game.
     * @return the number of players in this game.
     */
    int getNumberOfPlayers(){
        return seatedPlayers.length;
    }

    /**
     * Sets the playingOrder, that is the list that contains the order of the players for this round.
     * @param playingOrder  A List of Player that represent the order of the players for this round.
     */
    public void setPlayingOrder(List<Player> playingOrder) {
        this.playingOrder = playingOrder;
    }


    /**
     *
     * @return The current player's index in the playingOrder list.
     */
    public int getPlayingOrderIndex() {
        return playingOrderIndex;
    }

    /**
     * Sets the new playingOrderIndex, that is the index of the list that store the round order of the players.
     * @param playingOrderIndex The index of the next player in the round.
     */
    public void setPlayingOrderIndex(int playingOrderIndex) {
        this.playingOrderIndex = playingOrderIndex;
    }


    /**
     *
     * @return Return the Map that contains the mapping between Player and AssistantCard for this round.
     */
    public Map<Player, AssistantCard> getPlayerMaxSteps() {
        return playerMaxSteps;
    }

    /**
     * Resets the Map that contains the mapping between Player and AssistantCard for this round with an empty Map.
     */
    public void resetPlayerMaxSteps(){
        this.playerMaxSteps = new HashMap<>();
    }

    /**
     * @return The current Player, i.e. the player whose turn it is.
     */
    public Player getCurrentPlayer(){
        return playingOrder.get(playingOrderIndex);
    }


    /**
     *  It handles the activation of Character Cards.
     * @param currentState The GameState from which this method is called.
     * @param cardId The id of the card activated
     * @throws GameException if the activation of the card was not legal
     */
    void handleCard(GameState currentState , int cardId) throws GameException {
        if (!gameManager.isCardActive(cardId)){
            throw new GameException("This card is not present in the game");
        }

        if (gameManager.getUsedCard() != -1){
            throw new GameException("You already activated a card");
        }

        if (getCurrentPlayer().getCoinsOwned() < gameManager.findCardById(cardId).getPrice()){
            throw new GameException("You don't have enough coins");
        }


        gameManager.setUsedCard(cardId, getCurrentPlayer().getPlayerNumber());
       //todo maybe i can move this in a state.
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


    int convertPlayerNumberToPlayingIndex (int numberPlayer){
        for(int i =0; i< playingOrder.size(); i++){
            if (playingOrder.get(i).getPlayerNumber() == numberPlayer) return i;
        }
        return -1;
    }

}