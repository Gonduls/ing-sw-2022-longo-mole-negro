package it.polimi.ingsw.model;

public class GameManager {

    private static GameManager singleton;
    private Board board;
    private Cloud[] clouds;
    private Bag bag;
    private CharacterCard[] activeCards;
    private boolean[] usedCards;
    private Player[] players;
    private Professors professors;

    private GameManager(){}

    static public GameManager getInstance(){
        if(singleton == null)
            singleton = new GameManager();

        return singleton;
    }

    public Board getBoard() {
        return board;
    }

    public Cloud[] getClouds() {
        return clouds;
    }

    public Bag getBag() {
        return bag;
    }

    public CharacterCard[] getActiveCards() {
        return activeCards;
    }

    public boolean[] getUsedCards() {
        return usedCards;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Professors getProfessors() {
        return professors;
    }

    public Island[] getIslands() {
        return null;
    }

    public void getMotherNature() {

    }

    public int getTowerNumber() {
        return 0;
    }

    public TowerColor getTowerColor(Island island) {
        return null;
    }

    public void emptyCloudInPlayer() {

    }

    public void refillClouds() {

    }

    public void activateCharacterCars() {

    }

    public void moveMotherNature() {

    }


}
