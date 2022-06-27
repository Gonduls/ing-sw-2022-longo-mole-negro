package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.Color;

/**
 * Event created when a player moves a student from his entrance to his dining room
 */
public class MoveStudentFromEntranceToTableEvent extends GameEvent {

    Color color;
    int playerNumber;

    /**
     * Creates the MoveStudentFromEntranceToTableEvent event
     * @param color The color of the student moved
     * @param playerNumber The index of the player that is moving the student
     */
    public MoveStudentFromEntranceToTableEvent(Color color, int playerNumber) {
        this.color = color;
        this.playerNumber = playerNumber;
    }

    /**
     * @return The GameEventType of this game event (MOVE_STUDENT_FROM_ENTRANCE_TO_TABLE)
     */
    @Override
    public GameEventType getEventType() {
        return GameEventType.MOVE_STUDENT_FROM_ENTRANCE_TO_TABLE;
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
}
