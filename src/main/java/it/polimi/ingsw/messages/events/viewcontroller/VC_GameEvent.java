package it.polimi.ingsw.messages.events.viewcontroller;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

public  abstract class VC_GameEvent implements Message {


    @Override
    public MessageType getMessageType() {
        return MessageType.GAME_EVENT;
    }

    public abstract GameEventType getEventType();
    public abstract   String getPlayerName();

}

