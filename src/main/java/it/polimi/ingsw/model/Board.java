package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.ListIterator;
import java.util.Map;

public class Board {

    private ArrayList<Island> islands;
    private ListIterator<Island> iterator = islands.listIterator();

    private final Professors professors;

    private int motherNaturePosition;

    private int numberOfIslands;

    public Board(GameManager gameManager){
        numberOfIslands = 12;
        motherNaturePosition = 0;
        islands = new ArrayList<>(numberOfIslands);
        this.professors = gameManager.getProfessors();
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }

    boolean canBeMerged(int indexCurrentIsland, int indexOtherIsland) {
        return islands.get(indexCurrentIsland).getTower() == islands.get(indexOtherIsland).getTower();
    }

    void mergeIsland(int indexCurrentIsland){
        int indexPreviousIsland = (indexCurrentIsland+ numberOfIslands-1) % numberOfIslands ;
        int indexNextIsland = (indexCurrentIsland+ numberOfIslands+1) % numberOfIslands ;
        if (canBeMerged(indexCurrentIsland, indexPreviousIsland)){
            islands.get(indexCurrentIsland).unify(islands.get(indexPreviousIsland));
            islands.remove(indexPreviousIsland);
            numberOfIslands--;
        }

        if (canBeMerged(indexCurrentIsland, indexNextIsland)){
            islands.get(indexCurrentIsland).unify(islands.get(indexNextIsland));
            islands.remove(indexNextIsland);
            numberOfIslands--;
        }


    }

    TowerColor calculateInfluence(int index) {







        return TowerColor.BLACK;
    }
}

