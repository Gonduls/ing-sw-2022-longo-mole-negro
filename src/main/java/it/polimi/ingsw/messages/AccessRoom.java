package it.polimi.ingsw.messages;

public record AccessRoom(int id) implements Message {
    @Override
    public MessageType getMessageType() {
        return MessageType.ACCESS_ROOM;
    }

}
