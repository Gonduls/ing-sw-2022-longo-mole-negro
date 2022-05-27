package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.AssistantCard;

public class PlayAssistantCardEvent extends GameEvent {


    String playerName;
    AssistantCard ac;
    public PlayAssistantCardEvent(String playerName, AssistantCard ac){
        this.playerName=playerName;
        this.ac=ac;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.PLAY_ASSISTANT_CARD;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    public AssistantCard getAssistantCard(){
        return ac;
    }
}
