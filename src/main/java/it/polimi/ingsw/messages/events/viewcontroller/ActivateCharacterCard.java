package it.polimi.ingsw.messages.events.viewcontroller;

public class ActivateCharacterCard implements VC_GameEvent{
    String playerName;


    @Override
    public GameEventType getEventType() {
        return GameEventType.ACTIVATE_CHARACTER_CARD;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }





}
