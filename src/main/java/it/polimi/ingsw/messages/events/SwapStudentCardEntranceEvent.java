package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.Color;

public class SwapStudentCardEntranceEvent extends GameEvent {

    String playerName;

    Color studentFromCard;
    Color studentFromEntrance;

    int playerNumber;

    public SwapStudentCardEntranceEvent(Color studentFromCard, Color studentFromEntrance, int playerNumber) {
        this.studentFromCard = studentFromCard;
        this.studentFromEntrance = studentFromEntrance;
        this.playerNumber = playerNumber;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.SWAP_STUDENT_CARD_ENTRANCE;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }


    public Color getStudentFromCard() {
        return studentFromCard;
    }

    public Color getStudentFromEntrance() {
        return studentFromEntrance;
    }

}
