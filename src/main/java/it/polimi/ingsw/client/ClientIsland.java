package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.TowerColor;

import java.util.EnumMap;

public class ClientIsland{
    private final EnumMap<Color, Integer> students;
    private int towers, noEntry;
    private TowerColor tc;

    public ClientIsland(){
        students = new EnumMap<>(Color.class);
        for(Color color : Color.values())
            students.put(color, 0);
        noEntry = 0;
        towers = 0;
    }

    public EnumMap<Color, Integer> getStudents() {
        return students;
    }

    public int getNoEntry() {
        return noEntry;
    }

    public int getTowers() {
        return towers;
    }

    public TowerColor getTc() {
        return tc;
    }

    public void addNoEntry() {
        noEntry ++;
    }

    public void removeNoEntry() {
        noEntry --;
    }

    public void setTc(TowerColor tc) {
        this.tc = tc;
    }

    public void setTowers(int towers) {
        this.towers = towers;
    }
}
