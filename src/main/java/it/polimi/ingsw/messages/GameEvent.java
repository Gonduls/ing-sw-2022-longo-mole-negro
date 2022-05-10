package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.events.GameEventType;

public  abstract class GameEvent implements Message {


    @Override
    public MessageType getMessageType() {
        return MessageType.GAME_EVENT;
    }

    public abstract GameEventType getEventType();
    public abstract   String getPlayerName();

}

