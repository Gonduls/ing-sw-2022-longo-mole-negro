package it.polimi.ingsw.model;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class Bag extends StudentHolder {

    public Bag() {
        super(130, 26);
    }

    // may be broken idk
    public Color extractRandomStudent() {
        HashMap<Color, Integer> copy = getAllStudents();
        int randomNumber = (int) (Math.random() * copy.values().size());
        int bias = 0;
        Color toReturn = null;

        for (Color c : copy.keySet()) {
            copy.put(c, copy.get(c) + bias);
            bias = copy.get(c);
            if (randomNumber < bias) {
                toReturn = c;
                break;
            }
        }

        return toReturn;

    }


}

