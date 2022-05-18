package it.polimi.ingsw.messages;

public record ChangeTurn(int playingPlayer) implements Message{

    @Override
    public MessageType getMessageType() {
        return MessageType.CHANGE_TURN;
    }
}

