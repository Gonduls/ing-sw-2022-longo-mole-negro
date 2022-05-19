package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.AssistantCard;

public record PlayAssistantCard(AssistantCard assistantCard, int player) implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.PLAY_ASSISTANT_CARD;
    }
}

