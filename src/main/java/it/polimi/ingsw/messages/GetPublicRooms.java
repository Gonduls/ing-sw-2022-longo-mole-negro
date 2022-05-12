package it.polimi.ingsw.messages;

public class GetPublicRooms implements Message{
    private final int numberOfPlayers;
    private final Boolean expert;

    public GetPublicRooms(){
        this(-1, null);
    }

    public GetPublicRooms(int numberOfPlayers){
        this(numberOfPlayers, null);
    }

    public GetPublicRooms(boolean expert){
        this(-1, expert);
    }

    public GetPublicRooms(int numberOfPlayers, Boolean expert){
        this.numberOfPlayers = numberOfPlayers;
        this.expert = expert;
    }
    @Override
    public MessageType getMessageType() {
        return MessageType.GET_PUBLIC_ROOMS;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public Boolean getExpert() {
        return expert;
    }
}
