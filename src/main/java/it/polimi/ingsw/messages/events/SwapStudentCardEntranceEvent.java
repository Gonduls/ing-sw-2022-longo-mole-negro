package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.Color;

/**
 * Event created when a player has to swap a student from the card with a student
 * from the entrance of his school for the effect of a specific Character Card
 */
public class SwapStudentCardEntranceEvent extends GameEvent {

    Color studentFromCard;
    Color studentFromEntrance;
    int playerNumber;

    /**
     * Creates the SwapStudentCardEntranceEvent event
     * @param studentFromCard The color of the student taken from the card
     * @param studentFromEntrance The color of the student taken from the entrance
     * @param playerNumber The index of the player that is doing the swap
     */
    public SwapStudentCardEntranceEvent(Color studentFromCard, Color studentFromEntrance, int playerNumber) {
        this.studentFromCard = studentFromCard;
        this.studentFromEntrance = studentFromEntrance;
        this.playerNumber = playerNumber;
    }

    /**
     * @return The GameEventType of this game event (SWAP_STUDENT_CARD_ENTRANCE)
     */
    @Override
    public GameEventType getEventType() {
        return GameEventType.SWAP_STUDENT_CARD_ENTRANCE;
    }

    /**
     * @return The index of the player that is doing the swap
     */
    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * @return The color of the student taken from the card
     */
    public Color getStudentFromCard() {
        return studentFromCard;
    }

    /**
     * @return The color of the student taken from the entrance
     */
    public Color getStudentFromEntrance() {
        return studentFromEntrance;
    }

}
