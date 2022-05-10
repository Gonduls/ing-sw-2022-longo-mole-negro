package it.polimi.ingsw.messages;

public class PayPrice implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.PAY_PRICE;
    }
}

