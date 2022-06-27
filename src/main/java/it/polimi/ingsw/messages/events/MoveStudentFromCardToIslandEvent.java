package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.Color;

/**
 * Event created when a player moves a student from the Character card to an island
 */
public class MoveStudentFromCardToIslandEvent extends GameEvent {

    Color studentFromCard;
    int islandIndex;
    int playerNumber;

    /**
     * Creates the MoveStudentFromCardToIslandEvent event
     * @param studentFromCard The color of the student moved
     * @param islandIndex The index of the target island
     * @param playerNumber The index of the player that is moving the student
     */
    public MoveStudentFromCardToIslandEvent(Color studentFromCard, int islandIndex, int playerNumber) {
        this.studentFromCard = studentFromCard;
        this.islandIndex = islandIndex;
        this.playerNumber = playerNumber;
    }

    /**
     * @return The GameEventType of this game event (MOVE_STUDENT_FROM_CARD_TO_ISLAND)
     */
    @Override
    public GameEventType getEventType() {
        return GameEventType.MOVE_STUDENT_FROM_CARD_TO_ISLAND;
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
    public Color getStudentFromCard() {
        return studentFromCard;
    }

    /**
     * @return The index of the target island
     */
    public int getIslandIndex() {
        return islandIndex;
    }
}
