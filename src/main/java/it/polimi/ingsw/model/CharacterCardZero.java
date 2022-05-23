package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.exceptions.NoSpaceForStudentException;

public class CharacterCardZero extends CharacterCard {


    StudentHolder studentHolder;
    public CharacterCardZero(Bag bag) {
        this.id = 0;
        this.price = 1;
        studentHolder = new StudentHolder();
        Color colorTemp;
        for (int i = 0; i < 4; i++) {
            try {
                colorTemp = bag.extractRandomStudent();
                studentHolder.addStudent(colorTemp);
                this.modelObserver.addStudentToCard(this.id, colorTemp );
            } catch (NoSpaceForStudentException ignored) {
            }

        }
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
        return studentHolder;
    }

    @Override
    public void deactivateEffect() {

    }

    public CharacterState getCharacterState(RoundController context, GameState nextState){

        return new CharacterZeroState(context,2,nextState, this);

    }



}
