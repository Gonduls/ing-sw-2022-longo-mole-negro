package it.polimi.ingsw.messages;

public class MoveTower implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.MOVE_TOWER;
    }
}

