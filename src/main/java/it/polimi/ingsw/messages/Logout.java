package it.polimi.ingsw.messages;

public class Logout implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.LOGOUT;
    }

}
