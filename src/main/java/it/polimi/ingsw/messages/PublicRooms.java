package it.polimi.ingsw.messages;

import it.polimi.ingsw.server.RoomInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Message that holds all public rooms available that fitted the previous request
 * @param rooms The info requested
 */
public record PublicRooms(List<RoomInfo> rooms) implements Message, Serializable {

    /**
     * @return The MessageType of this message (PUBLIC_ROOMS)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.PUBLIC_ROOMS;
    }
}

