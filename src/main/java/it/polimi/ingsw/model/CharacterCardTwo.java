package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.server.ModelObserver;

/**
 *
 * È una carta con 6 studenti. scambia fino a 3 studenti presenti nel tuo ingresso
 */
public class CharacterCardTwo extends  CharacterCard{

    private  StudentHolder studentHolder;


    public CharacterCardTwo(Bag bag, ModelObserver modelObserver) {
        this.id = 2;
        this.price = 1;
        studentHolder = new StudentHolder();
        this.setModelObserver(modelObserver);
        Color colorTemp;
        for (int i = 0; i < 6; i++) {
            try {
                colorTemp = bag.extractRandomStudent();
                studentHolder.addStudent(colorTemp);
                this.modelObserver.addStudentToCard(this.id, colorTemp);
            } catch (NoSpaceForStudentException ignored) {
            }

        }
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

    }

    public CharacterState getCharacterState(RoundController context, GameState nextState){

        return new CharacterTwoState(context,3,nextState, this);

    }

}
