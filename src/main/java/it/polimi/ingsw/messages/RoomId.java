package it.polimi.ingsw.messages;

/**
 * Message that returns the id of the room that was created
 * @param id The room identifier
 */
public record RoomId(int id) implements Message {

    /**
     * @return The MessageType of this message
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.ROOM_ID;
    }
}