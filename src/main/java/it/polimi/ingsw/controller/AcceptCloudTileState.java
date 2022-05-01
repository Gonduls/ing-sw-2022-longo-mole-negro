package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.viewcontroller.VC_GameEvent;

/**
 * Third step in Action Phase
 *
 */
public class AcceptCloudTileState extends  GameState{
    AcceptCloudTileState(RoundController context, int numberOfEvents) {
        super(context, numberOfEvents);
    }

    @Override
    public boolean checkValidEvent(VC_GameEvent event) {
        return false;
    }

    @Override
    public void executeEvent(VC_GameEvent event) {

    }
}
