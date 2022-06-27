package it.polimi.ingsw.messages;

/**
 * Message
 * @param id The room identifier
 */
public record AccessRoom(int id) implements Message {

    /**
     * @return The MessageType of this message (ACCESS_ROOM)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.ACCESS_ROOM;
    }
}
