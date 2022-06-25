package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CharacterElevenState;
import it.polimi.ingsw.controller.CharacterState;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.controller.RoundController;
import it.polimi.ingsw.server.ModelObserver;

/**
 *
 * le scelte possibili sono: isole, studenti sulla carta, studenti fuori dalla carta, colore
 */

public class CharacterCardEleven extends CharacterCard{


    public CharacterCardEleven(ModelObserver modelObserver) {
    this.id=11;
    this.price=3;
    this.setModelObserver(modelObserver);
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


    public CharacterState getCharacterState(RoundController controller, GameState nextState){

        return new CharacterElevenState(controller,1, nextState,this);
    }
}
