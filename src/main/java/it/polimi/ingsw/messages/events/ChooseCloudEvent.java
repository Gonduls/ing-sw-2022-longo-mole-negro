package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;

/**
 * Event created when a player chooses a cloud at the end of his turn
 */
public class ChooseCloudEvent extends GameEvent {

    int cloudIndex;
    int playerNumber;

    /**
     * Creates the ChooseCloudEvent event
     * @param cloudIndex The index of the cloud
     * @param playerNumber The index of the player that
     */
    public ChooseCloudEvent(int cloudIndex, int playerNumber) {
        this.cloudIndex = cloudIndex;
        this.playerNumber = playerNumber;
    }

    /**
     * @return The GameEventType of this game event (CHOOSE_CLOUD)
     */
    @Override
    public GameEventType getEventType() {
        return GameEventType.CHOOSE_CLOUD;
    }

    /**
     * @return The index of the chosen cloud
     */
    public int getCloudIndex(){
        return cloudIndex;
    }

    /**
     * @return The index of the player that chose the cloud
     */
    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }
}
