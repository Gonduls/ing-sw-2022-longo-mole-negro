package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;

public class ChooseIslandEvent extends GameEvent {

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
