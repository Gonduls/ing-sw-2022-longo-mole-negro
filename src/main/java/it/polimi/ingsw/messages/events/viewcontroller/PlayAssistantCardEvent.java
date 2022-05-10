package it.polimi.ingsw.messages.events.viewcontroller;

import it.polimi.ingsw.model.AssistantCard;

public class PlayAssistantCardEvent extends VC_GameEvent {


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
        return null;
    }

    public AssistantCard getAssistantCard(){
        return ac;
    }
}
