package it.polimi.ingsw.model;

import it.polimi.ingsw.server.ModelObserver;

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
}
