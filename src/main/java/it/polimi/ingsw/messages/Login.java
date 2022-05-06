package it.polimi.ingsw.messages;

public class Login implements Message{
    private final String username;

    /**
     * Creates a Login message with "username" passed as message parameter
     * @param username: the username the client chose
     */
    public Login(String username){
        this.username = username;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.LOGIN;
    }

    public String getUsername() {
        return username;
    }
}
