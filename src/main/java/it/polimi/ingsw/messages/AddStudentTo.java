package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Color;

public class AddStudentTo implements Message{
    private final String where;
    private final Color color;

    @Override
    public MessageType getMessageType() {
        return MessageType.ADD_STUDENT_TO;
    }

    public AddStudentTo(String where, Color color){
        this.color = color;
        this.where = where;
    }

    public Color getColor() {
        return color;
    }

    public String getWhere() {
        return where;
    }
}

