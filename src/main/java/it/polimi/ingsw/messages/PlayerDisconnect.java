package it.polimi.ingsw.messages;

/**
 * Message that states that a player has disconnected from the game
 * @param username The name of the player
 */
public record PlayerDisconnect(String username) implements Message {

    /**
     * @return The MessageType of this message (PLAYER_DISCONNECT)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.PLAYER_DISCONNECT;
    }
}
