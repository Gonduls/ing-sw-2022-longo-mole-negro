package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Color;

public record MoveStudent(String from, String to, Color color) implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.MOVE_STUDENT;
    }
}

