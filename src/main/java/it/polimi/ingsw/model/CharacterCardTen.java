package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CharacterState;
import it.polimi.ingsw.controller.CharacterTenState;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.controller.RoundController;

/**
 *
 * scegli un colore, in questo turno il colore scelto non fornisce influenza
 *
 */

public class CharacterCardTen extends  CharacterCard{
     Color color;
    public CharacterCardTen() {
    this.id=10;
    this.price=3;
    color=null;

    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public void activateEffect() {

    }

    @Override
    public void applyEffect() {

    }

    @Override
    public StudentHolder getStudentHolder() {
        return null;
    }

    @Override
    public void deactivateEffect() {
        color = null;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public CharacterState getCharacterState(RoundController context, GameState nextState){

        return new CharacterTenState(context,1,nextState,this);
    }
}
