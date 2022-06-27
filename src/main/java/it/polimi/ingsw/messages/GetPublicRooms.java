package it.polimi.ingsw.messages;

/**
 * Message that states that the player wants to know which public rooms are available
 */
public class GetPublicRooms implements Message{
    private final int numberOfPlayers;
    private final Boolean expert;

    /**
     * If the player wants to know about all public rooms in general
     */
    public GetPublicRooms(){
        this(-1, null);
    }

    /**
     * If the player wants to know about all public rooms with numberOfPlayers players that can play
     * @param numberOfPlayers The number of players that can play
     */
    public GetPublicRooms(int numberOfPlayers){
        this(numberOfPlayers, null);
    }

    /**
     * If the player wants to know about all public rooms with a set game difficulty
     * @param expert The difficulty of the game
     */
    public GetPublicRooms(boolean expert){
        this(-1, expert);
    }

    /**
     * If the player wants to know about all public rooms with specific numberOfPlayers and difficulty
     * @param numberOfPlayers The number of players that can play
     * @param expert The difficulty of the game
     */
    public GetPublicRooms(int numberOfPlayers, Boolean expert){
        this.numberOfPlayers = numberOfPlayers;
        this.expert = expert;
    }

    /**
     * @return The MessageType of this message (GET_PUBLIC_ROOMS)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.GET_PUBLIC_ROOMS;
    }

    /**
     * @return The number of players that can play
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * @return The difficulty of the game
     */
    public Boolean getExpert() {
        return expert;
    }
}
