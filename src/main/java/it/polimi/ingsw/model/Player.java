package it.polimi.ingsw.model;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

public class Player {

    private final int playerNumber;
    private final String username;
    private int towersLeft;
    private final TowerColor towerColor;
    private final List<AssistantCard> deck;
    private int coinsOwned;
    private final School school;

    /**
     * Builder method for Player, also instantiates School and deck associated to player.
     * Given playerNumber and threePlayers, towersLeft and towerColor vary:
     * 3 players -> 6 towers to each player, first towerColor to first player, second color to second player and so on
     * !3 players -> 8 towers to player 1 and 2, first towerColor to player 1 (and 3), second towerColor to player 2 (and 4)
     * @param playerNumber: used to distinguish players (and teams)
     * @param username: player's unique identifier
     * @param threePlayers: used to identify a three players game
     */
    public Player(int playerNumber, String username, boolean threePlayers){
        this.playerNumber = playerNumber;
        this.username = username;
        deck = Arrays.stream(AssistantCard.values()).toList();
        coinsOwned = 1;

        if(threePlayers) {
            towersLeft = 6;
            towerColor = TowerColor.values()[playerNumber];
        }
        else{
            towerColor = TowerColor.values()[playerNumber % 2];
            towersLeft = 0;

            if(playerNumber < 3)
                towersLeft = 8;
        }

        school = new School(threePlayers);
    }

    /**
     * @return the number associated with the player
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * @return the player's unique username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the amount of towers that the player has yet to place
     */
    public int getTowersLeft() {
        return towersLeft;
    }

    /**
     * @return the TowerColor associated to the player
     */
    public TowerColor getTowerColor() {
        return towerColor;
    }

    /**
     * @return the assistant cards that the player has yet to play
     */
    public List<AssistantCard> getCardsLeft() {
        return deck;
    }

    /**
     * @return the amount of coins that the player has
     */
    public int getCoinsOwned() {
        return coinsOwned;
    }

    /**
     * It sets the tower count to "towers". It's mainly used when many towers are moved at the same time
     * 
     * @param towers: the amount of towers that the tower count is set to
     */
    public void setTowersNumber(int towers) {
        towersLeft = towers;
    }

    /**
     * Removes the picked card from the ones left in the player's deck
     * 
     * @param card: The assistant card picked
     * @throws InvalidParameterException: if the deck does not contain the designated card
     */
    public void pickCard(AssistantCard card) throws InvalidParameterException{
        if(deck.contains(card))
            deck.remove(card);
        else
            throw new InvalidParameterException("This card has already been used and is not in the deck anymore");
    }

    /**
     * Adds a coin to the ones owned by the player
     */
    public void addCoin() {
        coinsOwned ++;
    }

    /**
     * @param coinsNumber: the amount of coins that the player paid
     * @throws InvalidParameterException: if the player does not have enough coins to be able to pay
     */
    public void removeCoins(int coinsNumber) throws InvalidParameterException {
        if(coinsNumber > coinsOwned)
            throw new InvalidParameterException("There are not enough coins to execute this action");

        coinsOwned -= coinsNumber;
    }

    /**
     * @return the school owned by the player
     */
    public School getSchool(){
        return school;
    }
}
