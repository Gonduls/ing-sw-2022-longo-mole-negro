package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;

public class ChooseIslandEvent extends GameEvent {

    String player;
    int islandIndex;
    int playerNumber;

    public ChooseIslandEvent(int islandIndex, int playerNumber) {
        this.islandIndex = islandIndex;
        this.playerNumber = playerNumber;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.CHOOSE_ISLAND;
    }

    @Override
    public String getPlayerName() {
        return player;
    }

    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getIslandIndex(){
        return islandIndex;
    }
}
