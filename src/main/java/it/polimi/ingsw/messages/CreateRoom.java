package it.polimi.ingsw.messages;

public class CreateRoom implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.CREATE_ROOM;
    }
}

