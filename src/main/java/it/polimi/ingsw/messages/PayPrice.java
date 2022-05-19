package it.polimi.ingsw.messages;

public record PayPrice(int amount, int player) implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.PAY_PRICE;
    }
}

