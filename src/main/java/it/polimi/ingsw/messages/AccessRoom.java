package it.polimi.ingsw.messages;

public class AccessRoom implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.ACCESS_ROOM;
    }
}
