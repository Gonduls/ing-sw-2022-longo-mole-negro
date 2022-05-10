package it.polimi.ingsw.messages;

public class StartGame implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.START_GAME;
    }
}

