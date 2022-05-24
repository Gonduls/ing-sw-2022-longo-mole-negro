package it.polimi.ingsw.messages;

/**
 *
 *
 * @param add if true-> it adds the no entry, else it removes it
 * @param islandIndex
 */
public record NoEntry(boolean add, int islandIndex) implements Message {

    public MessageType getMessageType() {return MessageType.NO_ENTRY;}

}
