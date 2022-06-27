package it.polimi.ingsw.messages;

/**
 * Creates a Login message with "username" passed as message parameter
 * @param username The username the client chose
 */
public record Login(String username) implements Message {

    /**
     * @return The MessageType of this message (LOGIN)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.LOGIN;
    }
}
