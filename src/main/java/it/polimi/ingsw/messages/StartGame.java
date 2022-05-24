package it.polimi.ingsw.messages;

public class StartGame implements Message{
    private final int[] indexes;

    public StartGame(int[] indexes){
        this.indexes = indexes;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.START_GAME;
    }

    public int[] getIndexes() {
        return indexes;
    }
}

