package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CharacterState;
import it.polimi.ingsw.controller.CharacterThreeState;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.controller.RoundController;
import it.polimi.ingsw.server.ModelObserver;


/**
 *
 * When activated it swaps up to two students from the entrance to the dining room and vice-versa.
 * It moves up to 4 students.
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

    @Override
    public CharacterState getCharacterState(RoundController context, GameState nextState){

        return new CharacterThreeState(context,2,nextState, this);

    }



}
