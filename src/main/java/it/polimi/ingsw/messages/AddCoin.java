package it.polimi.ingsw.messages;

/**
 * Message that states that a coin has to be added to the target player
 * @param player The index of the player
 */
public record AddCoin(int player) implements Message{

    /**
     * @return The MessageType of this message (ADD_COIN)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.ADD_COIN;
    }
}

