package it.polimi.ingsw.messages;

public class RoomId implements Message{
    private final int id;

    @Override
    public MessageType getMessageType() {
        return MessageType.ROOM_ID;
    }

    public RoomId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

