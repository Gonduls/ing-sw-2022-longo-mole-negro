package it.polimi.ingsw.messages;

public record CreateRoom(int numberOfPlayers, boolean expert, boolean isPrivate) implements Message {
    @Override
    public MessageType getMessageType() {
        return MessageType.CREATE_ROOM;
    }

}

