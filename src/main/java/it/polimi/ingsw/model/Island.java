package it.polimi.ingsw.model;

public class Island {

    private int towerNumber;
    private TowerColor towerColor;
    private int noEntry; //we consider the possibility that a number of noEntries greater than 1 can be put on an Island

    public int getTowerNumber() {
        return towerNumber;
    }

    public TowerColor getTower() {
        return towerColor;
    }

    public int getNoEntry() { //GameManager needs to check if there is a noEntry
        return noEntry;
    }

    void addTower() {
        towerNumber++;
    }

    void setTowerColor(TowerColor tower) {
        towerColor = tower;
    }

    void addNoEntry() {
        noEntry++;
    }

    void removeNoEntry() {
        noEntry--;
    }
}
