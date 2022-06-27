package it.polimi.ingsw.messages;

/**
 * Message that states that a NoEntry has to be added or removed from a target island
 * @param add if true-> it adds the no entry, else it removes it
 * @param islandIndex The index of the target island
 */
public record NoEntry(boolean add, int islandIndex) implements Message {

    /**
     * @return The MessageType of this message (NO_ENTRY)
     */
    public MessageType getMessageType() {return MessageType.NO_ENTRY;}

}
