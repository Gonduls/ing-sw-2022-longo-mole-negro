package it.polimi.ingsw.messages;

import java.io.Serializable;

public interface Message extends Serializable {
    MessageType getMessageType();
}