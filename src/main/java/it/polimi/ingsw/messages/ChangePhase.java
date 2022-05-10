package it.polimi.ingsw.messages;

public class ChangePhase implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.CHANGE_PHASE;
    }
}
