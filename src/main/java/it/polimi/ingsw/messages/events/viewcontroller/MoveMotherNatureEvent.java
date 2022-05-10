package it.polimi.ingsw.messages.events.viewcontroller;

public class MoveMotherNatureEvent extends VC_GameEvent{

    String playerName;
    int amount;

    @Override
    public GameEventType getEventType() {
        return GameEventType.MOVE_MOTHER_NATURE;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    public int getAmount() {
        return amount;
    }
}
