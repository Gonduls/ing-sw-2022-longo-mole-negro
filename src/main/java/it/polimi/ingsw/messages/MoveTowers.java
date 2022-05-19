package it.polimi.ingsw.messages;

/**
 * Parsing positions has to be in the form of: "ISLAND:1" and "PLAYER:1",
 * where player has to be the one that can hold towers (in case of four players)
 */
public record MoveTowers(String from, String to, int amount) implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.MOVE_TOWERS;
    }
}

