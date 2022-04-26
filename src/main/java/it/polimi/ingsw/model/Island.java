package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;

public class Island extends StudentHolder {


    private int towerNumber;
    private TowerColor towerColor;
    private int noEntry; //we consider the possibility that a number of noEntries greater than 1 can be put on an Island

    /**
     * Initializes an Island to hold 0 towers and 0 noEntry (needed for expert mode)
     */
    public Island(){
        towerNumber=0;
        noEntry=0;
    }

    /**
     * @return the number of towers
     */
    int getTowerNumber() {
        return towerNumber;
    }

    /**
     * @return the TowerColor of the towers on this Island (null if no towers are present)
     */
    TowerColor getTower() {
        return towerColor;
    }

    /**
     * @return returns the amount of noEntry present on this Island
     */
    int getNoEntry() { //GameManager needs to check if there is a noEntry
        return noEntry;
    }

    /**
     * Adds a tower to the Island.
     * Only called to add the first Tower onto an Island, other towers are added via unify().
     */
    void addTower() {
        towerNumber++;
    }

    /**
     * Sets the TowerColor of the towers on this island to "tower"
     * @param tower: the target TowerColor color
     */
    void setTowerColor(TowerColor tower) {
        towerColor = tower;
    }

    /**
     * Adds a noEntry to the island
     */
    void addNoEntry() {
        noEntry++;
    }

    /**
     * Removes a noEntry from the island
     */
    void removeNoEntry() {
        noEntry--;
    }

    /**
     * Unifies two Islands together by adding their towerNumber and
     * moving students from islandToUnifyWith to this Island.
     * Does not remove islandToUnifyWith from all Islands nor removes towers from it,
     * but does remove all students from it.
     *
     * @param islandToUnifyWith: the Island that is to be absorbed from the current Island
     */
    void unify (Island islandToUnifyWith){
        towerNumber += islandToUnifyWith.getTowerNumber();

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


    /**
     * @return a string representing an Island contents
     */
    @Override
    public String toString(){
        String toReturn = "TowerColor: "+  (towerColor== null? "null ":towerColor.toString()) +" TowerNumber: " + getTowerNumber();
        StringBuilder temp = new StringBuilder();

        for(Color c: Color.values()){

            temp.append(" ").append(c.toString()).append(": ").append(this.getStudentByColor(c));
        }
        return toReturn + temp;


    }

}
