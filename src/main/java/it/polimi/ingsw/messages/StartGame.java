package it.polimi.ingsw.messages;

public record StartGame(boolean expert) implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.START_GAME;
    }

}

