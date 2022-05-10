package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;

public class ChooseCloudTileEvent extends GameEvent {

    String playerName;
    int cloudIndex;
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
}
