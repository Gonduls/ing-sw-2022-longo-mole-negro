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
        return switch (id){
            case 0 -> "Take 1 student from this card and place it on an island of your choice";
            case 1 -> "You can move Mother Nature up to 2 additional islands";
            case 2 -> "You can exchange up to 3 students between this card and your entrance";
            case 3 -> "You can exchange up to 2 students between your entrance and your dining room";
            case 4 -> "During this turn, you take control of Professors even if you match the number of students of the previous owner";
            case 5 -> "You can place a No Entry on a island. If Mother Nature ends on an island with a No Entry: remove one No Entry, no influence is calculated";
            case 6 -> "During the influence calculation this turn, you count as having two more influence";
            case 7 -> "Take one student from this card and place it in your dining room";
            case 8 -> "Choose an island and resolve influence calculation as if Mother Nature had ended her turn there";
            case 9 -> "This turn Towers do not count towards influence";
            case 10 -> "Choose a color of a student: this turn students of that color do not count towards influence";
            case 11 -> "Choose a color: every player (including yourself) must remove 3 (or all if they have less than 3) students of that color from their Dining Rooms";
            default -> "No such card";
        };
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
