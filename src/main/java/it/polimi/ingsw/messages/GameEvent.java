package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.events.GameEventType;

/**
 * Any kind of action the player can take in a game
 */
public  abstract class GameEvent implements Message {

    /**
     * @return The MessageType of this message (GAME_EVENT)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.GAME_EVENT;
    }

    /**
     * @return The GameEventType of this game event
     */
    public abstract GameEventType getEventType();

    /**
     * @return The player's username
     */
    public abstract String getPlayerName();

    /**
     * @return The index of the player
     */
    public abstract int getPlayerNumber();
}

