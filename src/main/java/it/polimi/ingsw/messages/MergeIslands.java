package it.polimi.ingsw.messages;

public record MergeIslands(int firstIslandIndex, int secondIslandIndex) implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.MERGE_ISLANDS;
    }
}

