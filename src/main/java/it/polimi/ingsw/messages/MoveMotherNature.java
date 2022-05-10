package it.polimi.ingsw.messages;

public class MoveMotherNature implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.MOVE_MOTHER_NATURE;
    }
}

