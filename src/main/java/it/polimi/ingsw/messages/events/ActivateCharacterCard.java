package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;

public class ActivateCharacterCard extends GameEvent {

    public ActivateCharacterCard(int cardId, int playerNumber) {
        this.cardId = cardId;
        this.playerNumber = playerNumber;
    }

    String playerName;
    int cardId;
    int playerNumber;


    @Override
    public GameEventType getEventType() {
        return GameEventType.ACTIVATE_CHARACTER_CARD;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getCardId() {
        return cardId;
    }

}
