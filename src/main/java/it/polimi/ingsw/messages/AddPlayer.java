package it.polimi.ingsw.messages;

/**
 * Message that states that a player has been added to the room
 * @param username The player username
 * @param position The index of the player
 */
public record AddPlayer(String username, int position) implements Message {

    /**
     * @return The MessageType of this message (ADD_PLAYER)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.ADD_PLAYER;
    }

}

