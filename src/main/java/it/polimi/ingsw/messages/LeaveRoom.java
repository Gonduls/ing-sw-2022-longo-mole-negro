package it.polimi.ingsw.messages;

public class LeaveRoom implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.LEAVE_ROOM;
    }
}

