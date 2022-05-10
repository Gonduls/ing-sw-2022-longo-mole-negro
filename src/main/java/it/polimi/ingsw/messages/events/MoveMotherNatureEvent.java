package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;

public class MoveMotherNatureEvent extends GameEvent {

    String playerName;
    int amount;

    @Override
    public GameEventType getEventType() {
        return GameEventType.MOVE_MOTHER_NATURE;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    public int getAmount() {
        return amount;
    }
}
