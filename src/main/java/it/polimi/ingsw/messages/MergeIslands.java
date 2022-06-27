package it.polimi.ingsw.messages;

/**
 * Message that states that the two target islands have to be merged
 * @param firstIslandIndex The index of the first target island
 * @param secondIslandIndex The index of the second target island
 */
public record MergeIslands(int firstIslandIndex, int secondIslandIndex) implements Message{


    /**
     * @return The MessageType of this message (MERGE_ISLANDS)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.MERGE_ISLANDS;
    }
}

