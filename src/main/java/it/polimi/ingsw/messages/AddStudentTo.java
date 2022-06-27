package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Color;

/**
 * Message that states that a student has to be added to a certain position
 * @param to The position the student has to added to
 * @param color The color of the student
 */
public record AddStudentTo(String to, Color color) implements Message {

    /**
     * @return The MessageType of this message (ADD_STUDENT_TO)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.ADD_STUDENT_TO;
    }
}

