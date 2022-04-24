package it.polimi.ingsw.model;

public class Island extends StudentHolder {


    private int towerNumber;
    private TowerColor towerColor;
    private int noEntry; //we consider the possibility that a number of noEntries greater than 1 can be put on an Island

    public Island(){
        super();
        towerNumber=0;
        noEntry=0;
    }
    int getTowerNumber() {
        return towerNumber;
    }

    TowerColor getTower() {
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


    void unify (Island islandToUnifyWith){
        // magna le torri
        towerNumber += islandToUnifyWith.getTowerNumber();
        // magna gli students
         for (Color color : Color.values()) {
             for(int i = islandToUnifyWith.getStudentByColor(color); i>0; i--){
                 try {
                     islandToUnifyWith.moveStudentTo(color, this);
                 } catch (NoSpaceForStudentException e) {
                     System.out.println("It would seem the island has a maximum capacity, impossible");
                 }
                 catch (NoSuchStudentException e) {
                     System.out.println("Grabbed a non-existent student from island to be merged");
                 }
             }
         }
    }
}
