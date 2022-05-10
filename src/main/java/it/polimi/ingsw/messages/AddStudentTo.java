package it.polimi.ingsw.messages;

public class AddStudentTo implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.ADD_STUDENT_TO;
    }
}

