package it.polimi.ingsw.messages;

public class ActivateCharacterCard implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.ACTIVATE_CHARACTER_CARD;
    }
}

