package it.polimi.ingsw.messages;

public record MoveMotherNature(int position) implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.MOVE_MOTHER_NATURE;
    }
}

