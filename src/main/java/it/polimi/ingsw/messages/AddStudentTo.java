package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Color;

public record AddStudentTo(String to, Color color) implements Message {

    @Override
    public MessageType getMessageType() {
        return MessageType.ADD_STUDENT_TO;
    }
}

