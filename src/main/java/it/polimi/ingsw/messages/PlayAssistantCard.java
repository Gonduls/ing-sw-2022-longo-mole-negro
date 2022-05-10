package it.polimi.ingsw.messages;

public class PlayAssistantCard implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.PLAY_ASSISTANT_CARD;
    }
}

