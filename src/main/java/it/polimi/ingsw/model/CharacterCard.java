package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CharacterState;
import it.polimi.ingsw.controller.CharacterZeroState;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.controller.RoundController;

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

    public int getId() {
        return id;
    }


    public  CharacterState getCharacterState(RoundController context, GameState nextState){
        return null;
    }
}
