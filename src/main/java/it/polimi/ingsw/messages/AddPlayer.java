package it.polimi.ingsw.messages;

public class AddPlayer implements Message{
    private final String username;
    private final int position;

    @Override
    public MessageType getMessageType() {
        return MessageType.ADD_PLAYER;
    }

    public AddPlayer(String username, int position){
        this.position = position;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getPosition() {
        return position;
    }
}

