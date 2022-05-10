package it.polimi.ingsw.messages.events.viewcontroller;

public class ChooseIslandEvent extends VC_GameEvent {

    String player;
    int islandIndex;
    @Override
    public GameEventType getEventType() {
        return GameEventType.CHOOSE_ISLAND;
    }

    @Override
    public String getPlayerName() {
        return player;
    }

    public int getIslandIndex(){
        return islandIndex;
    }
}
