package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.AssistantCard;

/**
 * Event created when a player plays an Assistant Card
 */
public class PlayAssistantCardEvent extends GameEvent {

    AssistantCard ac;
    int playerNumber;

    /**
     * Creates the PlayAssistantCardEvent event
     * @param ac The Assistant Card played
     * @param playerNumber The index of the player that played the card
     */
    public PlayAssistantCardEvent(AssistantCard ac, int playerNumber) {
        this.ac = ac;
        this.playerNumber = playerNumber;
    }

    /**
     * @return The GameEventType of this game event (PLAY_ASSISTANT_CARD)
     */
    @Override
    public GameEventType getEventType() {
        return GameEventType.PLAY_ASSISTANT_CARD;
    }

    /**
     * @return The index of the player that played the card
     */
    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * @return The Assistant Card played
     */
    public AssistantCard getAssistantCard(){
        return ac;
    }
}
