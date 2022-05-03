package it.polimi.ingsw.messages;

public class Nack extends Message{
    private final String errorMessage;

    public Nack(String errorMessage){
        this.errorMessage = errorMessage;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.NACK;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
