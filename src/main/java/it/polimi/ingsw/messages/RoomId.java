package it.polimi.ingsw.messages;

public record RoomId(int id) implements Message {
    @Override
    public MessageType getMessageType() {
        return MessageType.ROOM_ID;
    }

}

