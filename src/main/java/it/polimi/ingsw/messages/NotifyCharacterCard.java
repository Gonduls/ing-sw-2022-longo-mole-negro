package it.polimi.ingsw.messages;

public record NotifyCharacterCard(int card) implements  Message {
    @Override
    public MessageType getMessageType() {
        return MessageType.NOTIFY_CHARACTER_CARD;
    }

}
