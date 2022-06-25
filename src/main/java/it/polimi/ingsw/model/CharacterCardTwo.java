package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.server.ModelObserver;

/**
 *
 * It's  a card with 6 students on it. When activated  a player can choose up to three students on the card to swap with
 * as many students on their entrance.
 *  This card  generates a Character State.
 */
public class CharacterCardTwo extends  CharacterCard{



    public CharacterCardTwo(Bag bag, ModelObserver modelObserver) {
        this.id = 2;
        this.price = 1;
        sh = new StudentHolder();
        this.setModelObserver(modelObserver);
        Color colorTemp;
        for (int i = 0; i < 6; i++) {
            try {
                colorTemp = bag.extractRandomStudent();
                this.sh.addStudent(colorTemp);
                this.modelObserver.addStudentToCard(this.id, colorTemp);
            } catch (NoSpaceForStudentException ignored) {
            }

        }
    }



    @Override
    public int getPrice() {
        return this.price;
    }




    @Override
    public StudentHolder getStudentHolder() {
        return this.sh;
    }

    @Override
    public void deactivateEffect() {
    //there is nothing to deactivate
    }

    @Override
    public CharacterState getCharacterState(RoundController context, GameState nextState){

        return new CharacterTwoState(context,3,nextState, this);

    }

}
