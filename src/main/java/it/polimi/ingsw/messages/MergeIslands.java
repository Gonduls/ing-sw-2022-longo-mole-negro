package it.polimi.ingsw.messages;

public class MergeIslands implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.MERGE_ISLANDS;
    }
}

