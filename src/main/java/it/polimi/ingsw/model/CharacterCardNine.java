package it.polimi.ingsw.model;


/**
 *
 *durante il conteggio dell’influenza, le torri non vengono calcolate
 *
 */

public class CharacterCardNine extends  CharacterCard{

    public CharacterCardNine(){
        this.id =9;
        this.price=3;
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
