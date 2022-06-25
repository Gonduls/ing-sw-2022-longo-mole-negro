package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CharacterSevenState;
import it.polimi.ingsw.controller.CharacterState;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.controller.RoundController;
import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.server.ModelObserver;

/**
 * It's a card with 4 students on it.
 * The player can pick a student from the card and put it into the entrance.
 *  This card  generates a Character State.
 */

public class CharacterCardSeven extends  CharacterCard{


    public CharacterCardSeven(Bag bag, ModelObserver modelObserver){
      this.id=7;
      this.price=2;
      this.sh = new StudentHolder();
      Color colorTemp;
      this.setModelObserver(modelObserver);
       for(int i =0;i<4;i++) {
           try {
               colorTemp = bag.extractRandomStudent();
               sh.addStudent(colorTemp);
               modelObserver.addStudentToCard(this.id, colorTemp);
           }
           catch (NoSpaceForStudentException ignored){
                //there is always space in the card.
           }

       }


    }
    @Override
    public int getPrice() {
        return this.price;
    }



    @Override
    public StudentHolder getStudentHolder() {
        return this.sh;
    }

    @Override
    public void deactivateEffect() {
    // there is nothing to deactivate
    }

    @Override
    public CharacterState getCharacterState(RoundController context, GameState nextState) {
        return new CharacterSevenState(context,1, nextState,this);
    }
}