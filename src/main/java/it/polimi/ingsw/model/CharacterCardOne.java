package it.polimi.ingsw.model;

public class CharacterCardOne extends  CharacterCard{

    public CharacterCardOne() {
        this.price = 1;
        this.id =1;

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
