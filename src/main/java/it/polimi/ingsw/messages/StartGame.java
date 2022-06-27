package it.polimi.ingsw.messages;

/**
 * Message that states that the game can start
 * @param expert The difficulty of the game
 */
public record StartGame(boolean expert) implements Message{

    /**
     * @return The MessageType of this message (START_GAME)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.START_GAME;
    }

}

