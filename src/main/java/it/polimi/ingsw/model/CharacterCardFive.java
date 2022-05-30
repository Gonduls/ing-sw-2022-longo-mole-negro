package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.server.ModelObserver;

/**
 * It has 4 for no entry token on it. When activated a player can choose an island on which put a token from the card.
 *
 */
public class CharacterCardFive extends CharacterCard{

    public CharacterCardFive(ModelObserver modelObserver) {
      this.noEntryToken = 4;
      this.id = 5;
      this.price=2;
      this.setModelObserver(modelObserver);
    }

    private int noEntryToken;


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


    public int getNoEntryToken() {
        return noEntryToken;
    }


    public void removeNoEntryToken(){
        this.noEntryToken--;
    }

    @Override
    public CharacterState getCharacterState(RoundController context, GameState nextState){

        return new CharacterFiveState(context,1,nextState, this);

    }

}