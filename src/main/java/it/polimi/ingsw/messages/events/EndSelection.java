package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;

/**
 * Event created when a player no longer wants to use the effect of a specific Character card
 */
public class EndSelection extends GameEvent {
    int playerNumber;

    /**
     * Creates the EndSelection event
     * @param playerNumber The index of the player that ended his usage of the card
     */
    public EndSelection(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    /**
     * @return The GameEventType of this game event (END_SELECTION)
     */
    @Override
    public GameEventType getEventType() {
        return GameEventType.END_SELECTION;
    }

    /**
     * @return The index of the player that ended his usage of the card
     */
    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }
}
