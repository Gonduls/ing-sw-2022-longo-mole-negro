package it.polimi.ingsw.model;

import it.polimi.ingsw.server.ModelObserver;

/**
 * During this turn, the current player takes control of any professors even if they have
 * the same number of students as the player who currently controls them.
 * Clarification: This rule is only checked when a student's movement happens.
 * This card doesn't generate a Character State
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
    public StudentHolder getStudentHolder() {
        return null;
    }

    @Override
    public void deactivateEffect() {
    }
}
