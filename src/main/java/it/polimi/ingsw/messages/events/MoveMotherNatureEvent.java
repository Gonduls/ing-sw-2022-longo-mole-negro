package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.messages.MessageType;

public class MoveMotherNatureEvent extends GameEvent {

    String playerName;
    int amount;

    int playerNumber;

    public MoveMotherNatureEvent(int amount, int playerNumber) {
        this.amount = amount;
        this.playerNumber = playerNumber;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.MOVE_MOTHER_NATURE;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }
}
