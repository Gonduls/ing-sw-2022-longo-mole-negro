package it.polimi.ingsw.messages;

public class MoveStudent implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.MOVE_STUDENT;
    }
}

