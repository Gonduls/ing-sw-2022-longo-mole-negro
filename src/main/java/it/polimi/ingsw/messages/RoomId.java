package it.polimi.ingsw.messages;

public class RoomId implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.ROOM_ID;
    }
}

