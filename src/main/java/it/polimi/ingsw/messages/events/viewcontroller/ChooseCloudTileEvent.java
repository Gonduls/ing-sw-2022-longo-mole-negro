package it.polimi.ingsw.messages.events.viewcontroller;

public class ChooseCloudTileEvent implements VC_GameEvent{

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
