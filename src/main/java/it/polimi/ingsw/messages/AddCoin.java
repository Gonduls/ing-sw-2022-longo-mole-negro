package it.polimi.ingsw.messages;

public class AddCoin implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.ADD_COIN;
    }
}

