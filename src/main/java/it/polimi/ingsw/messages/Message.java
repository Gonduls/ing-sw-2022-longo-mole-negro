package it.polimi.ingsw.messages;

import java.io.Serializable;

/**
 * Interface for all messages
 */
public interface Message extends Serializable {

    /**
     * @return The MessageType of this message
     */
    MessageType getMessageType();
}