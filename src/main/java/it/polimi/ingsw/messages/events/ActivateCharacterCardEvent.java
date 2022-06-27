package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;

/**
 * Event created when a player activates a character card
 */
public class ActivateCharacterCardEvent extends GameEvent {
    int cardId;
    int playerNumber;

    /**
     * Creates the ActivateCharacterCard event
     * @param cardId he CharacterCard identifier
     * @param playerNumber The index of the player that activated the card
     */
    public ActivateCharacterCardEvent(int cardId, int playerNumber) {
        this.cardId = cardId;
        this.playerNumber = playerNumber;
    }

    /**
     * @return The GameEventType of this game event (ACTIVATE_CHARACTER_CARD)
     */
    @Override
    public GameEventType getEventType() {
        return GameEventType.ACTIVATE_CHARACTER_CARD;
    }

    /**
     * @return The index of the player that activated the card
     */
    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * @return he CharacterCard identifier of the activated card
     */
    public int getCardId() {
        return cardId;
    }
}
