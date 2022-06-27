package it.polimi.ingsw.messages;

/**
 * Message that states that the previous message was not valid
 * @param errorMessage A string explaining why the message was not valid
 */
public record Nack(String errorMessage) implements Message {

    /**
     * @return The MessageType of this message (NACK)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.NACK;
    }
}
