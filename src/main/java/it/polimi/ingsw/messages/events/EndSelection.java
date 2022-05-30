package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;

public class EndSelection extends GameEvent {
    @Override
    public GameEventType getEventType() {
        return GameEventType.END_SELECTION;
    }

    @Override
    public String getPlayerName() {
        return null;
    }
}
