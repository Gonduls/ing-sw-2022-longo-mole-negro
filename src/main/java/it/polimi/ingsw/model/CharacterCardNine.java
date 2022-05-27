package it.polimi.ingsw.model;


import it.polimi.ingsw.server.ModelObserver;

/**
 *
 *durante il conteggio dell’influenza, le torri non vengono calcolate
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
