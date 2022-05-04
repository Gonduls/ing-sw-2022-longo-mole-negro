package it.polimi.ingsw.messages;

public class Ack implements Message{
    public MessageType getMessageType(){
        return MessageType.ACK;
    }
}
