package it.polimi.ingsw.messages;

/**
 * Message that states that a character card has been added to the game
 * @param card The CharacterCard identifier
 */
public record NotifyCharacterCard(int card) implements  Message {

    /**
     * @return The MessageType of this message (NOTIFY_CHARACTER_CARD)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.NOTIFY_CHARACTER_CARD;
    }

}
