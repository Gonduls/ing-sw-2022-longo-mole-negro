package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.Color;

public class ChooseColorEvent extends GameEvent {
    String player;
    Color color;
    int playerNumber;

    public ChooseColorEvent(Color color, int playerNumber) {
        this.color = color;
        this.playerNumber = playerNumber;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.CHOOSE_COLOR;
    }

    @Override
    public String getPlayerName() {
        return player;
    }

    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }


    public Color getColor(){
        return color;
    }
}
