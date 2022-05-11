package it.polimi.ingsw.server;

public class RoomInfo {
    private final int id;
    private final int numberOfPlayers;
    private final boolean expert;
    private int currentPlayers;

    RoomInfo(int id, int numberOfPlayers, boolean expert){
        this.currentPlayers = 1;
        this.expert = expert;
        this.id = id;
        this.numberOfPlayers = numberOfPlayers;
    }

    void addPlayer(){
        currentPlayers++;
    }

    public int getId() {
        return id;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public boolean getExpert(){
        return expert;
    }
}
