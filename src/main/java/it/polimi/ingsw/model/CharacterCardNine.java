package it.polimi.ingsw.model;


import it.polimi.ingsw.server.ModelObserver;

/**
 * Towers do not count towards the influence.
 * This card doesn't generate a Character State.
 *
 */

public class CharacterCardNine extends  CharacterCard{

    public CharacterCardNine(ModelObserver modelObserver){
        this.id =9;
        this.price=3;
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
    // there is nothing to deactivate
    }
}
