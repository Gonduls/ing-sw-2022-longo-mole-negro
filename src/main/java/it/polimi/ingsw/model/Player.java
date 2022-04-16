package it.polimi.ingsw.model;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

public class Player {

    private final int playerNumber;
    private final String nickname;
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
     * @param nickname: player's unique identifier
     * @param threePlayers: used identify a three players game
     */
    public Player(int playerNumber, String nickname, boolean threePlayers){
        this.playerNumber = playerNumber;
        this.nickname = nickname;
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

    public int getPlayerNumber() {
        return playerNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public int getTowersLeft() {
        return towersLeft;
    }

    public TowerColor getTowerColor() {
        return towerColor;
    }

    public List<AssistantCard> getCardsLeft() {
        return deck;
    }

    public int getCoinsOwned() {
        return coinsOwned;
    }

    public void setTowersNumber(int towers) {
        towersLeft = towers;
    }

    public void pickCard(AssistantCard card) throws InvalidParameterException{
        if(deck.contains(card))
            deck.remove(card);
        else
            throw new InvalidParameterException("This card has already been used and is not in the deck anymore");
    }

    public void addCoin() {
        coinsOwned ++;
    }

    public void removeCoins(int coinsNumber) throws InvalidParameterException {
        if(coinsNumber > coinsOwned)
            throw new InvalidParameterException("There are not enough coins to execute this action");

        coinsOwned -= coinsNumber;
    }

    public School getSchool(){
        return school;
    }


}
