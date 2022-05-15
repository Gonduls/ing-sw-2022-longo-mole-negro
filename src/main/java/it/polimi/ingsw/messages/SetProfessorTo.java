package it.polimi.ingsw.messages;

public class SetProfessorTo implements Message{


    @Override
    public MessageType getMessageType() {
        return MessageType.SET_PROFESSOR_TO;
    }
}

