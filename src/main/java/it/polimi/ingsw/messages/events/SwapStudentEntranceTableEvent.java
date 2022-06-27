package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.Color;

/**
 * Event created when a player has to swap a student from the dining room of his school with
 * a student from the entrance of his school for the effect of a specific Character Card
 */
public class SwapStudentEntranceTableEvent extends GameEvent {

    Color studentFromEntrance;
    Color studentFromTable;
    int playerNumber;

    /**
     * Creates the SwapStudentEntranceTableEvent event
     * @param studentFromEntrance The color of the student taken from the entrance
     * @param studentFromTable The color of the student taken from the dining room
     * @param playerNumber The index of the player that is doing the swap
     */
    public SwapStudentEntranceTableEvent(Color studentFromEntrance, Color studentFromTable, int playerNumber) {
        this.studentFromEntrance = studentFromEntrance;
        this.studentFromTable = studentFromTable;
        this.playerNumber = playerNumber;
    }

    /**
     * @return The GameEventType of this game event (SWAP_STUDENT_ENTRANCE_TABLE)
     */
    @Override
    public GameEventType getEventType() {
        return GameEventType.SWAP_STUDENT_ENTRANCE_TABLE;
    }

    /**
     * @return The color of the student taken from the entrance
     */
    public Color getStudentFromEntrance() {
        return studentFromEntrance;
    }

    /**
     * @return The color of the student taken from the dining room
     */
    public Color getStudentFromTable() {
        return studentFromTable;
    }

    /**
     * @return The index of the player that is doing the swap
     */
    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }
}