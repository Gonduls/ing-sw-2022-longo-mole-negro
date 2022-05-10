package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.Color;

public class ChooseColorEvent extends GameEvent {
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
