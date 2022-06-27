package it.polimi.ingsw.messages;

/**
 * Message that states that the player wants to leave the current room that he is in
 */
public class LeaveRoom implements Message{

    /**
     * @return The MessageType of this message (LEAVE_ROOM)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.LEAVE_ROOM;
    }
}

