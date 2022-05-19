package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Color;

public record SetProfessorTo(Color color, int player) implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.SET_PROFESSOR_TO;
    }
}

