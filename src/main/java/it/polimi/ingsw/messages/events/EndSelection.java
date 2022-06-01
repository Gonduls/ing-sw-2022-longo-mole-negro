package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;

public class EndSelection extends GameEvent {

    String playerName;
    int playerNumber;

    public EndSelection(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.END_SELECTION;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }
}
