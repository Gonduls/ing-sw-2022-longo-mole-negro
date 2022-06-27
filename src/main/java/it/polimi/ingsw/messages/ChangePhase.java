package it.polimi.ingsw.messages;

import it.polimi.ingsw.controller.GamePhase;

/**
 * Message that states that the current phase is gamePhase
 * @param gamePhase The phase the game is now in
 */
public record ChangePhase(GamePhase gamePhase) implements Message{

    /**
     * @return The MessageType of this message (CHANGE_PHASE)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.CHANGE_PHASE;
    }
}
