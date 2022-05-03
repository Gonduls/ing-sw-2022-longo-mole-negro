package it.polimi.ingsw.messages;

public class Ack extends Message{
    public MessageType getMessageType(){
        return MessageType.ACK;
    }
}
