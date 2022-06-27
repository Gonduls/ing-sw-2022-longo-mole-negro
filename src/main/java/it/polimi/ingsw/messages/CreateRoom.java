package it.polimi.ingsw.messages;

/**
 * Message that states that a room has been created
 * @param numberOfPlayers The number of players that can play in this room
 * @param expert The difficulty of the game
 * @param isPrivate True if the room is private, false otherwise
 */
public record CreateRoom(int numberOfPlayers, boolean expert, boolean isPrivate) implements Message {

    /**
     * @return The MessageType of this message (CREATE_ROOM)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.CREATE_ROOM;
    }

}

