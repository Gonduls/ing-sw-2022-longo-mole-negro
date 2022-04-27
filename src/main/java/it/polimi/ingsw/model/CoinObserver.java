package it.polimi.ingsw.model;

import java.util.EnumMap;

public class CoinObserver implements StudentHolderObserver{
    private EnumMap<Color, Integer> oldStudents;
    private final StudentHolder observed;
    private final Player player;

    /**
     * Observer that gives a coin to the target player every time a 3rd, 6th,
     * or 9th student is placed in a specific color in the Player's School's tables
     * @param player: the Player that has the School that contains the tables to be observed
     */
    CoinObserver(Player player){
        this.observed = player.getSchool().getStudentsAtTables();
        this.player = player;
        oldStudents = observed.getAllStudents();
    }

    /**
     * If a 3rd, 6th, or 9th student is placed in a specific color in the
     * Player's School's tables, it gives a coin to that player
     */
    public void update(){
        EnumMap<Color, Integer> newStudents = observed.getAllStudents();
        for(Color color : Color.values()){
            if(newStudents.get(color) > oldStudents.get(color) && newStudents.get(color)%3 ==0){
                player.addCoin();
            }
        }

        oldStudents = newStudents;
    }
}
