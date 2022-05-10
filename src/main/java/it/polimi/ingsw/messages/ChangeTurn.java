package it.polimi.ingsw.messages;

public class ChangeTurn implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.CHANGE_TURN;
    }
}

