package it.polimi.ingsw.messages;

/**
 * Message that states that the target CharacterCard has been activated by the player
 * @param characterCardIndex The CharacterCard identifier
 * @param player The index of the player
 */
public record ActivateCharacterCard(int characterCardIndex, int player) implements Message{

    /**
     * @return The MessageType of this message (ACTIVATE_CHARACTER_CARD)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.ACTIVATE_CHARACTER_CARD;
    }
}

