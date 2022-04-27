package it.polimi.ingsw.model;

public interface CharacterCard {


    public int getPrice();
    public void activateEffect();
    public void applyEffect();
    public StudentHolder getStudentHolder();
    public void deactivateEffect();

}
