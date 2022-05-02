package it.polimi.ingsw.events.viewcontroller;

import it.polimi.ingsw.model.Color;

public class ChooseColorEvent implements VC_GameEvent{
    String player;
    Color color;
    @Override
    public GameEventType getEventType() {
        return GameEventType.CHOOSE_COLOR;
    }

    @Override
    public String getPlayerName() {
        return player;
    }


    public Color getColor(){
        return color;
    }
}
