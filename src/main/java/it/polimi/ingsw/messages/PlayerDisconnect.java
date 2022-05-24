package it.polimi.ingsw.messages;

public record PlayerDisconnect(String username) implements Message {

    @Override
    public MessageType getMessageType() {
        return MessageType.PLAYER_DISCONNECT;
    }
}
