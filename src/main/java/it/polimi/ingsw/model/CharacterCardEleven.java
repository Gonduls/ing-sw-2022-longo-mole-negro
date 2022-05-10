package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CharacterElevenState;
import it.polimi.ingsw.controller.CharacterState;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.controller.RoundController;

/**
 *
 * le scelte possibili sono: isole, studenti sulla carta, studenti fuori dalla carta, colore
 */

public class CharacterCardEleven extends CharacterCard{


    public CharacterCardEleven() {
    this.id=11;
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


    public CharacterState getCharacterState(RoundController controller, GameState nextState){

        return new CharacterElevenState(controller,1, nextState,this);
    }
}
