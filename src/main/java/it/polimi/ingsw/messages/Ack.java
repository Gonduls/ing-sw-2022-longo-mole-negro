package it.polimi.ingsw.messages;

/**
 * Message that states that the previous message was valid
 */
public class Ack implements Message{

    /**
     * @return The MessageType of this message (ACK)
     */
    public MessageType getMessageType(){
        return MessageType.ACK;
    }
}