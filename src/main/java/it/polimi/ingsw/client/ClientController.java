package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import it.polimi.ingsw.messages.*;

public class ClientController {

    void updateCModel(Message message) throws UnexpectedMessageException{
        if(! doesUpdate(message))
            throw(new UnexpectedMessageException("Did not receive a message that modifies the model"));
    }

    boolean doesUpdate(Message message){
        MessageType type = message.getMessageType();
        return (type != MessageType.ACK &&
                type != MessageType.NACK &&
                type != MessageType.ROOM_ID &&
                type != MessageType.PUBLIC_ROOMS);
    }
}
