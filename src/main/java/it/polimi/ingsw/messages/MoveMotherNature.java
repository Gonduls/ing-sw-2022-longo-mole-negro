package it.polimi.ingsw.messages;

/**
 * Message that states that MotherNature has to be moved to the target position
 * @param position The index of the target island
 */
public record MoveMotherNature(int position) implements Message{

    /**
     * @return The MessageType of this message (MOVE_MOTHER_NATURE)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.MOVE_MOTHER_NATURE;
    }
}

