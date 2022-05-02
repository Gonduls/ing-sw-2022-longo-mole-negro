package it.polimi.ingsw.model;

public abstract class CharacterCard {

    StudentHolder sh;
    int price;
    int id;

    public CharacterCard  (){


    }

    public abstract int getPrice();
    public abstract void activateEffect();
    public abstract void applyEffect();
    public abstract StudentHolder getStudentHolder();
    public abstract void deactivateEffect();

}
