package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.AssistantCard;

/**
 * Message that states that a player has played a target assistant card
 * @param assistantCard The target Assistant card
 * @param player The index of the player
 */
public record PlayAssistantCard(AssistantCard assistantCard, int player) implements Message{

    /**
     * @return The MessageType of this message (PLAY_ASSISTANT_CARD)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.PLAY_ASSISTANT_CARD;
    }
}

