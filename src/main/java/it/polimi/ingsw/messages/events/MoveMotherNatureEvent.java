package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;

/**
 * Event created when a player moves Mother Nature
 */
public class MoveMotherNatureEvent extends GameEvent {

    int amount;
    int playerNumber;

    /**
     * Creates the MoveMotherNatureEvent event
     * @param amount The amount of steps Mother Nature has to take
     * @param playerNumber The index of the player that moves Mother Nature
     */
    public MoveMotherNatureEvent(int amount, int playerNumber) {
        this.amount = amount;
        this.playerNumber = playerNumber;
    }

    /**
     * @return The GameEventType of this game event (MOVE_MOTHER_NATURE)
     */
    @Override
    public GameEventType getEventType() {
        return GameEventType.MOVE_MOTHER_NATURE;
    }

    /**
     * @return The amount of steps Mother Nature has to take
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @return The index of the player that moves Mother Nature
     */
    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }
}
