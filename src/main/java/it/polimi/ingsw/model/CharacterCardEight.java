package it.polimi.ingsw.model;


import it.polimi.ingsw.controller.CharacterEightState;
import it.polimi.ingsw.controller.CharacterState;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.controller.RoundController;

/**
 *
 *
 * scegli un isola e calcola la sua influenza come se madre natura fosse caduta lì.
 */
public class CharacterCardEight extends CharacterCard {

    public CharacterCardEight(){
        this.id = 8;
        this.price=3;
    }

    @Override
    public int getPrice() {
        return price;
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


    public CharacterState getCharacterState(RoundController context, GameState nextState){

        return new CharacterEightState(context,1,nextState) ;

    }

}