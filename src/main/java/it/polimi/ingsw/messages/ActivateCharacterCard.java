package it.polimi.ingsw.messages;

public record ActivateCharacterCard(int characterCardIndex, int player) implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.ACTIVATE_CHARACTER_CARD;
    }
}

