package it.polimi.ingsw.model;
public class Player {

    private int playerNumber;
    private String nickname;
    private int towersLeft;
    private TowerColor towerColor;
    private AssistantCard[] deck;
    private int coinsOwned;
    private School school;

    //todo a constructor

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
    //todo
    }

    public void pickCard(AssistantCard card) {
    //todo
    }

    public void addCoin() {
    //todo
    }

    public void removeCoins(int coinsNumber) {
    //todo
    }

    public School getSchool(){
        return school;
    }


}
