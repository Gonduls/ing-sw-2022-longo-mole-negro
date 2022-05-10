package it.polimi.ingsw.model;


import java.util.EnumMap;

/**
 *
 *
 * durante il calcolo dell’influenza, chi ha attivato la carta, ha +2 sul calcolo dell’influenza
*/
public class CharacterCardSix extends CharacterCard{

    private Player player;


    public CharacterCardSix(){
        this.id=6;
        this.price=2;
        player = null;
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
