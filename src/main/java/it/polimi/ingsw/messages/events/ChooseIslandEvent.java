package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;

/**
 * Event created when a player chooses an island for the effect of a specific Character Card
 */
public class ChooseIslandEvent extends GameEvent {

    int islandIndex;
    int playerNumber;

    /**
     * Creates the ChooseIslandEvent event
     * @param islandIndex The index of the target island
     * @param playerNumber The index of the player that chose the island
     */
    public ChooseIslandEvent(int islandIndex, int playerNumber) {
        this.islandIndex = islandIndex;
        this.playerNumber = playerNumber;
    }

    /**
     * @return The GameEventType of this game event (CHOOSE_ISLAND)
     */
    @Override
    public GameEventType getEventType() {
        return GameEventType.CHOOSE_ISLAND;
    }

    /**
     * @return The index of the player that chose the island
     */
    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * @return The index of the target island
     */
    public int getIslandIndex(){
        return islandIndex;
    }
}
