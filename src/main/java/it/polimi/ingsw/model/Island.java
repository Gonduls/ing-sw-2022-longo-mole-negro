package it.polimi.ingsw.model;

public class Island extends StudentHolder {

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


    void unify (Island islandToUnifyWith){
        // magna le torri
        towerNumber += islandToUnifyWith.getTowerNumber();
        // magna gli students
         for (Color c: Color.values()) {
             try {
                 islandToUnifyWith.moveStudentTo(c, this);
             } catch (NoSpaceForStudentException e) {System.out.println("impossibile che succeda 1 ");}
             catch (NoSuchStudentException e) {System.out.println("impossibile che succeda 2 ");}
         }

    }
}
