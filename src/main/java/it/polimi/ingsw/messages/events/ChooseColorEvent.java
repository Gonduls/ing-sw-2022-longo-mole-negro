package it.polimi.ingsw.messages.events;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.Color;

/**
 * Event created when a player chooses a color fot the effect of a specific Character Card
 */
public class ChooseColorEvent extends GameEvent {
    Color color;
    int playerNumber;

    /**
     * Creates the ChooseColorEvent event
     * @param color The chosen color
     * @param playerNumber The index of the player that chose the color
     */
    public ChooseColorEvent(Color color, int playerNumber) {
        this.color = color;
        this.playerNumber = playerNumber;
    }

    /**
     * @return The GameEventType of this game event (CHOOSE_COLOR)
     */
    @Override
    public GameEventType getEventType() {
        return GameEventType.CHOOSE_COLOR;
    }

    /**
     * @return The index of the player that chose the color
     */
    @Override
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * @return The chosen color
     */
    public Color getColor(){
        return color;
    }
}
