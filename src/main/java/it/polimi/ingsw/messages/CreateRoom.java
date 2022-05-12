package it.polimi.ingsw.messages;

public class CreateRoom implements Message{
    private final int numberOfPlayers;
    private final boolean expert, isPrivate;

    @Override
    public MessageType getMessageType() {
        return MessageType.CREATE_ROOM;
    }

    public CreateRoom(int numberOfPlayers, boolean expert, boolean isPrivate){
        this.expert = expert;
        this.numberOfPlayers = numberOfPlayers;
        this.isPrivate = isPrivate;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public boolean isExpert() {
        return expert;
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}

