package it.polimi.ingsw.messages;

import it.polimi.ingsw.controller.GamePhase;

public record ChangePhase(GamePhase gamePhase) implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.CHANGE_PHASE;
    }
}
