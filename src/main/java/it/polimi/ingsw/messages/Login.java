package it.polimi.ingsw.messages;

public class Login extends Message{
    private final String username;

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
