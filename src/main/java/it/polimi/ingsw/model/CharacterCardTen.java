package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CharacterState;
import it.polimi.ingsw.controller.CharacterTenState;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.controller.RoundController;
import it.polimi.ingsw.server.ModelObserver;

/**
 *
 * Choose a color, in this turn the chosen color will not count towards the influence.
 *  This card  generates a Character State.
 */

public class CharacterCardTen extends  CharacterCard{
     Color color;
    public CharacterCardTen(ModelObserver modelObserver) {
    this.id=10;
    this.price=3;
    color=null;
    this.setModelObserver(modelObserver);
    }

    @Override
    public int getPrice() {
        return this.price;
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

    @Override
    public CharacterState getCharacterState(RoundController context, GameState nextState){

        return new CharacterTenState(context,1,nextState,this);
    }
}
