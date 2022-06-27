package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Color;

/**
 * Message that states that a target student has to be moved from a place to another
 * @param from Where the student has to be taken from
 * @param to Where the student has to be added to
 * @param color The color of the student
 */
public record MoveStudent(String from, String to, Color color) implements Message{

    /**
     * @return The MessageType of this message (MOVE_STUDENT)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.MOVE_STUDENT;
    }
}

