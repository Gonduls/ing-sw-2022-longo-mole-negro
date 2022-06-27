package it.polimi.ingsw.server;

import java.io.Serializable;

/**
 * Holds information about a single room
 */
public class RoomInfo implements Serializable {
    private final int id;
    private final int numberOfPlayers;
    private final boolean expert;
    private final boolean isPrivate;
    private int currentPlayers;

    /**
     * It sets starting parameters
     * @param id The room identifier
     * @param numberOfPlayers The number of players that can populate the room
     * @param expert The difficulty of the game
     * @param isPrivate True if the room is private, false otherwise
     */
    RoomInfo(int id, int numberOfPlayers, boolean expert, boolean isPrivate){
        this.currentPlayers = 0;
        this.expert = expert;
        this.id = id;
        this.numberOfPlayers = numberOfPlayers;
        this.isPrivate = isPrivate;
    }

    /**
     * It adds one to the current number of players in the room
     */
    void addPlayer(){
        currentPlayers++;
    }

    /**
     * @return The room identifier
     */
    public int getId() {
        return id;
    }

    /**
     * @return The current number of players in the room
     */
    public int getCurrentPlayers() {
        return currentPlayers;
    }

    /**
     * @return The number of players that can populate the room
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * @return The difficulty of the game
     */
    public boolean getExpert(){
        return expert;
    }

    /**
     * @return false if the room can still accept players, true otherwise
     */
    public boolean isFull(){
        return currentPlayers == numberOfPlayers;
    }

    /**
     * @return True if the room is private, false otherwise
     */
    public boolean isPrivate() {
        return isPrivate;
    }

    /**
     * @return A string representation of RoomInfo
     */
    @Override
    public String toString() {
        return "RoomInfo{ id=" + id + ", players= " + currentPlayers + "/" + numberOfPlayers +", expert=" + expert +" }";
    }
}
