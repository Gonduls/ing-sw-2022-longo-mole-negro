package it.polimi.ingsw.messages;

public record AddPlayer(String username, int position) implements Message {
    @Override
    public MessageType getMessageType() {
        return MessageType.ADD_PLAYER;
    }

}

