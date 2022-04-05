package it.polimi.ingsw.model;

public class Board {

    private Island[] islands;
    private int motherNaturePosition;

    public Island[] getIslands() {
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
