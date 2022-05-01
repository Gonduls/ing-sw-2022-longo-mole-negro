package it.polimi.ingsw.events.viewcontroller;

import it.polimi.ingsw.events.viewcontroller.GameEventType;

public interface VC_GameEvent {

    GameEventType getEventType();
    String getPlayerName();

}

