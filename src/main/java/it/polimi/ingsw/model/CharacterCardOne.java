package it.polimi.ingsw.model;

import it.polimi.ingsw.server.ModelObserver;

/**
 * In this turn the current player can move Mother Nature up to 2 additional
 * islands than is indicated by the played Assistant card.
 * This card doesn't generate a Character State.
 */
public class CharacterCardOne extends  CharacterCard{

    public CharacterCardOne(ModelObserver modelObserver) {
        this.price = 1;
        this.id =1;
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
