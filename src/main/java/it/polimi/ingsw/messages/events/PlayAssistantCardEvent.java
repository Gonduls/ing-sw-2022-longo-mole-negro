package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.AssistantCard;

public class PlayAssistantCardEvent extends GameEvent {


    String playerName;
    AssistantCard ac;

    int playerNumber;
    @Deprecated
    public PlayAssistantCardEvent(String playerName, AssistantCard ac){
        this.playerName=playerName;
        this.ac=ac;
    }

    public PlayAssistantCardEvent(AssistantCard ac, int playerNumber) {
        this.ac = ac;
        this.playerNumber = playerNumber;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.PLAY_ASSISTANT_CARD;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }

    public AssistantCard getAssistantCard(){
        return ac;
    }
}
