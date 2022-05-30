package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.CharacterState;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.controller.RoundController;
import it.polimi.ingsw.server.ModelObserver;

public abstract class CharacterCard {

    StudentHolder sh;
    int price;
    int id;
    ModelObserver modelObserver;

    public abstract int getPrice();
    public abstract void activateEffect();
    public abstract void applyEffect();
    public abstract StudentHolder getStudentHolder();
    public abstract void deactivateEffect();

    public int getId() {
        return id;
    }

    public static boolean hasStudentHolder(int id){
        return (id == 0 || id == 2 || id == 7);
    }

    public static String description(int id){
        // todo: add all card descriptions
        String response;
        switch (id){
            case 0 -> response = "generic card description";
            default -> response = "even more generic card description";
        }
        return(response);
    }

    public  CharacterState getCharacterState(RoundController context, GameState nextState){
        return null;
    }

    /**
     * The increase in price happens only once.
     * We structured the cards so that the initial price is ID%4 + 1
     */
    public void increasePrice(){
        if(this.price == (this.id % 4) + 1 ) {
            this.price++;
        }
    }

    public void setModelObserver(ModelObserver modelObserver){
        this.modelObserver= modelObserver;
    }
}
