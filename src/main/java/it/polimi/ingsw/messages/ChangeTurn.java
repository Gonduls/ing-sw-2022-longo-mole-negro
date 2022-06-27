package it.polimi.ingsw.messages;

/**
 * Message that states that the turn now is to be played by the target player
 * @param playingPlayer The index of the player
 */
public record ChangeTurn(int playingPlayer) implements Message{

    /**
     * @return The MessageType of this message (CHANGE_TURN)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.CHANGE_TURN;
    }
}

