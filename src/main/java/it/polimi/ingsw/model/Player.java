package it.polimi.ingsw.model;
public class Player {

    private final int playerNumber;
    private final String nickname;
    private int towersLeft;
    private final TowerColor towerColor;
    private final AssistantCard[] deck;
    private int coinsOwned;
    private final School school;

    public Player(int playerNumber, String nickname, boolean threePlayers){
        this.playerNumber = playerNumber;
        this.nickname = nickname;
        deck = AssistantCard.values();
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
