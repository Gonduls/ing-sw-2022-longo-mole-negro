package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.server.RoomInfo;

import java.util.List;

public class ClientController {
    private boolean turn = true;
    private final UI ui;

    public ClientController(UI ui){
        this.ui = ui;
    }

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

    boolean myTurn(){
        return turn;
    }

    void showPublicRooms(List<RoomInfo> rooms){
        // todo: print all room info
    }

    void showMessage(Message message){
        //todo
    }
}
