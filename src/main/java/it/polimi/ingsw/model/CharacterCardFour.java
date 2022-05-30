package it.polimi.ingsw.model;

import it.polimi.ingsw.server.ModelObserver;

/**
 * It changes the rule for assigning the professors from a strict ">" to a ">="
 *
 */
public class CharacterCardFour extends  CharacterCard{

    public CharacterCardFour(ModelObserver modelObserver) {
        this.id = 4;
        this.price = 2;
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
