package it.polimi.ingsw.messages;

public record AddCoin(int player) implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.ADD_COIN;
    }
}

