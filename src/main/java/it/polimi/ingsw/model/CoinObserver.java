package it.polimi.ingsw.model;

import java.util.HashMap;

public class CoinObserver implements StudentHolderObserver{
    private HashMap<Color, Integer> oldStudents;
    private final StudentHolder observed;
    private final Player player;

    CoinObserver(Player player, StudentHolder observed){
        this.observed = observed;
        this.player = player;
        oldStudents = observed.getAllStudents();
    }

    public void update(){
        HashMap<Color, Integer> newStudents = observed.getAllStudents();
        for(Color color : Color.values()){
            if(newStudents.get(color) > oldStudents.get(color) && newStudents.get(color) ==0){
                player.addCoin();
            }
        }

        oldStudents = newStudents;
    }
}
