package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.Color;

public class MoveStudentFromCardToIslandEvent extends GameEvent {

    String playerName;


    Color studentFromCard;
    int islandIndex;
    int playerNumber;

    public MoveStudentFromCardToIslandEvent(Color studentFromCard, int islandIndex, int playerNumber) {
        this.studentFromCard = studentFromCard;
        this.islandIndex = islandIndex;
        this.playerNumber = playerNumber;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.MOVE_STUDENT_FROM_CARD_TO_ISLAND;
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

    public int getIslandIndex() {
        return islandIndex;
    }
}
