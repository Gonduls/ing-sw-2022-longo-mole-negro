package it.polimi.ingsw.messages.events.viewcontroller;

public class ActivateCharacterCard extends VC_GameEvent {
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
