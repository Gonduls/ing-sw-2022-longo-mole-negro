package it.polimi.ingsw.model;


import it.polimi.ingsw.server.ModelObserver;

import java.util.EnumMap;

/**
 * During the calculation for the influence, the current player has +2 bonus.
 *  This card doesn't generate a Character State.
*/
public class CharacterCardSix extends CharacterCard{

    private Player player;


    public CharacterCardSix(ModelObserver modelObserver){
        this.id=6;
        this.price=2;
        player = null;
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
        removePlayer();
    }


    public void setPlayer(Player player){
        this.player = player;
    }

    public void removePlayer(){
        this.player=null;
    }

    public TowerColor getTowerColor(){
        return player.getTowerColor();
    }





}
