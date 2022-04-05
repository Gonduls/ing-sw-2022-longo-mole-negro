package main.java.it.polimi.ingsw;

public class Player {

    private int playerNumber;
    private String nickname;
    private int towersLeft;
    private TowerColor towerColor;
    private AssistantCard[] deck;
    private int coinsOwned;

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

    public AssistantCard[] getCardsLeft() {
        return deck;
    }

    public int getCoinsOwned() {
        return coinsOwned;
    }

    public void setTowersNumber(int towers) {

    }

    public void pickCard(AssistantCard card) {

    }

    public void addCoin() {

    }

    public void removeCoins(int coinsNumber) {

    }
}
