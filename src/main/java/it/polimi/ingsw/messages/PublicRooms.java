package it.polimi.ingsw.messages;

public class PublicRooms implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.PUBLIC_ROOMS;
    }
}

