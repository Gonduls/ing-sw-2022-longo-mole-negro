package it.polimi.ingsw.messages;

import it.polimi.ingsw.server.RoomInfo;

import java.util.List;

public class PublicRooms implements Message{
    private List<RoomInfo> rooms;

    public PublicRooms(List<RoomInfo> rooms){
        this.rooms = rooms;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.PUBLIC_ROOMS;
    }

    public List<RoomInfo> getRooms() {
        return rooms;
    }
}

