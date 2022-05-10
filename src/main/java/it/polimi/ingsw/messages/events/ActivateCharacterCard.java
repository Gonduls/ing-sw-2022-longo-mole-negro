package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;

public class ActivateCharacterCard extends GameEvent {
    String playerName;
    int cardId;


    @Override
    public GameEventType getEventType() {
        return GameEventType.ACTIVATE_CHARACTER_CARD;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    public int getCardId() {
        return cardId;
    }
}
