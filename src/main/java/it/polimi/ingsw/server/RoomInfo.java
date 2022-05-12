package it.polimi.ingsw.server;

public class RoomInfo {
    private final int id;
    private final int numberOfPlayers;
    private final boolean expert;
    private final boolean isPrivate;
    private int currentPlayers;

    RoomInfo(int id, int numberOfPlayers, boolean expert, boolean isPrivate){
        this.currentPlayers = 0;
        this.expert = expert;
        this.id = id;
        this.numberOfPlayers = numberOfPlayers;
        this.isPrivate = isPrivate;
    }

    void addPlayer(){
        currentPlayers++;
    }

    void removePlayer(){
        currentPlayers--;
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

    public boolean isFull(){
        return currentPlayers == numberOfPlayers;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    @Override
    public String toString() {
        return "RoomInfo{" +
                "id=" + id +
                ", numberOfPlayers=" + numberOfPlayers +
                ", expert=" + expert +
                ", isPrivate=" + isPrivate +
                ", currentPlayers=" + currentPlayers +
                "}\n";
    }
}
