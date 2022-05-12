package it.polimi.ingsw.messages;

public class AccessRoom implements Message{
    private final int id;

    @Override
    public MessageType getMessageType() {
        return MessageType.ACCESS_ROOM;
    }

    public AccessRoom(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
