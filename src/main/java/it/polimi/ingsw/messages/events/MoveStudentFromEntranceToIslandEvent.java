package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.Color;

/**
 * Event created when a player moves a student from his entrance to an island
 */
public class MoveStudentFromEntranceToIslandEvent extends GameEvent {

    Color color;
    int indexIsland;
    int playerNumber;

    /**
     * Creates the MoveStudentFromEntranceToIslandEvent event
     * @param color The color of the student moved
     * @param indexIsland The index of the target island
     * @param playerNumber The index of the player that is moving the student
     */
    public MoveStudentFromEntranceToIslandEvent(Color color, int indexIsland, int playerNumber) {
        this.color = color;
        this.indexIsland = indexIsland;
        this.playerNumber = playerNumber;
    }

    /**
     * @return The GameEventType of this game event (MOVE_STUDENT_FROM_ENTRANCE_TO_ISLAND)
     */
    @Override
    public GameEventType getEventType() {
        return GameEventType.MOVE_STUDENT_FROM_ENTRANCE_TO_ISLAND;
    }

    /**
     * @return The index of the player that is moving the student
     */
    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * @return The color of the student moved
     */
    public Color getColor(){
       return color;
    }

    /**
     * @return The index of the target island
     */
    public int getIndexIsland() {
        return indexIsland;
    }






}
