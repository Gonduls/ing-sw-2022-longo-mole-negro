package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;

public class ChooseCloudTileEvent extends GameEvent {


    String playerName;
    int cloudIndex;
    int playerNumber;

    public ChooseCloudTileEvent(int cloudIndex, int playerNumber) {
        this.cloudIndex = cloudIndex;
        this.playerNumber = playerNumber;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.CHOOSE_CLOUD_TILE;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    public int getCloudIndex(){
        return cloudIndex;
    }

    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }
}
