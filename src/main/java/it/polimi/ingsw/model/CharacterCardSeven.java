package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CharacterSevenState;
import it.polimi.ingsw.controller.CharacterState;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.controller.RoundController;
import it.polimi.ingsw.exceptions.NoSpaceForStudentException;

/**
 *
 * è una carta con 4 studenti.
 * prendi 1 studente dalla carta e mettilo nella tua sala.
 * Pesca un nuovo studente per la carta
 */

public class CharacterCardSeven extends  CharacterCard{


    public CharacterCardSeven(Bag bag){
      this.id=7;
      this.price=2;
      Color colorTemp;
       for(int i =0;i<4;i++) {
           try {
               colorTemp = bag.extractRandomStudent();
               sh.addStudent(colorTemp);
               modelObserver.addStudentToCard(this.id, colorTemp);
           }
           catch (NoSpaceForStudentException ignored){

           }

       }


    }
    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public void activateEffect() {

    }

    @Override
    public void applyEffect() {

    }

    @Override
    public StudentHolder getStudentHolder() {
        return this.sh;
    }

    @Override
    public void deactivateEffect() {

    }

    @Override
    public CharacterState getCharacterState(RoundController context, GameState nextState) {
        return new CharacterSevenState(context,1, nextState,this);
    }
}