package it.polimi.ingsw.messages;

/**
 * Message that states that the player wants to leave the game
 */
public class Logout implements Message{

    /**
     * @return The MessageType of this message (LOGOUT)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.LOGOUT;
    }

}
