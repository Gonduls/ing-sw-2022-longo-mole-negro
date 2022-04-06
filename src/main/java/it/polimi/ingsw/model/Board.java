package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.ListIterator;

public class Board {

    private ArrayList<Island> islands;
    private ListIterator<Island> iterator = islands.listIterator();
    private int motherNaturePosition;

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }

    boolean canBeMerged(int index) {


        return true;
    }

    void mergeIsland(int index) {

    }

    TowerColor calculateInfluence(int index) {
        TowerColor tower = TowerColor.BLACK;
        return tower;
    }
}
