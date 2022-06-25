package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.TowerColor;

import java.util.EnumMap;

/**
 * A representation of islands in the client model
 */
public class ClientIsland{
    private final EnumMap<Color, Integer> students;
    private int towers, noEntry;
    private TowerColor tc;

    /**
     * Creates all objects and sets parameters to 0
     */
    public ClientIsland(){
        students = new EnumMap<>(Color.class);
        for(Color color : Color.values())
            students.put(color, 0);
        noEntry = 0;
        towers = 0;
    }

    /**
     * @return The Hashmap containing the students present in the island
     */
    public EnumMap<Color, Integer> getStudents() {
        return students;
    }

    /**
     * @return the number of "no entry" placed on the island
     */
    public int getNoEntry() {
        return noEntry;
    }

    /**
     * @return the number of the towers present in the island
     */
    public int getTowers() {
        return towers;
    }

    /**
     * @return the color of the towers present on the island
     */
    public TowerColor getTc() {
        return tc;
    }

    /**
     * Adds a "no entry"
     */
    public void addNoEntry() {
        noEntry ++;
    }

    /**
     * Removes a "no entry"
     */
    public void removeNoEntry() {
        noEntry --;
    }

    /**
     * Sets TowerColor according to parameter
     * @param tc The final TowerColor
     */
    public void setTc(TowerColor tc) {
        this.tc = tc;
    }

    /**
     * Sets the tower number
     * @param towers The final number of towers on the island
     */
    public void setTowers(int towers) {
        this.towers = towers;
    }
}
