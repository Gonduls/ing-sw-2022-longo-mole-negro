package it.polimi.ingsw.messages;

/**
 * Message that states that an amount of towers has to be moved from a location to another
 * Parsing positions has to be in the form of: "ISLAND:1" and "PLAYER:1",
 * where player has to be the one that can hold towers (in case of four players)
 * @param from Where the towers have to be taken from
 * @param to Where the towers have to be added to
 * @param amount The amount of towers to move
 */
public record MoveTowers(String from, String to, int amount) implements Message{

    /**
     * @return The MessageType of this message (MOVE_TOWERS)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.MOVE_TOWERS;
    }
}

