package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CharacterState;
import it.polimi.ingsw.controller.CharacterThreeState;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.controller.RoundController;
import it.polimi.ingsw.server.ModelObserver;


/**
 * scambia la posizione di fino a 2 studenti dalla sala all’ingresso o viceversa.
 * (nb in tutto si muoveranno 4 studenti max)
 */

public class CharacterCardThree extends CharacterCard{



    public CharacterCardThree(ModelObserver modelObserver) {
        this.id = 3;
        this.price = 1;
        this.setModelObserver(modelObserver);

    }


    @Override
    public int getPrice() {
        return price;
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

    }

    public CharacterState getCharacterState(RoundController context, GameState nextState){

        return new CharacterThreeState(context,2,nextState, this);

    }



}
