package it.polimi.ingsw.messages;

public class AddPlayer implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.ADD_PLAYER;
    }
}

