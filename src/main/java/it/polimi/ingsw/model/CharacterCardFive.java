package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.server.ModelObserver;

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

    public void setNoEntryToken(int noEntryToken) {
        this.noEntryToken = noEntryToken;
    }

    public void removeNoEntryToken(){
        this.noEntryToken--;
    }

    public CharacterState getCharacterState(RoundController context, GameState nextState){

        return new CharacterFiveState(context,1,nextState, this);

    }

}