package it.polimi.ingsw.model;

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
       for(int i =0;i<4;i++) {
           try {sh.addStudent(bag.extractRandomStudent());}
           catch (NoSpaceForStudentException ignored){}

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
}