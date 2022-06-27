package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Color;

/**
 * Message that states that a target professor is now owned by the target player
 * @param color The color of the professor
 * @param player The index of the player
 */
public record SetProfessorTo(Color color, int player) implements Message{

    /**
     * @return The MessageType of this message (SET_PROFESSOR_TO)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.SET_PROFESSOR_TO;
    }
}