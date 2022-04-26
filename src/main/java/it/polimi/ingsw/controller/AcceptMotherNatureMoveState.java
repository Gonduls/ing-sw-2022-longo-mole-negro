package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.VC_GameEvent;

public class AcceptMotherNatureMoveState extends GameState {
    AcceptMotherNatureMoveState(RoundController context, int numberOfEvents) {
        super(context, numberOfEvents);
    }

    @Override
    public boolean checkValidEvent(VC_GameEvent event) {
        return false;
    }
}
