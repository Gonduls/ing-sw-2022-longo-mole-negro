package it.polimi.ingsw.model;

import it.polimi.ingsw.Log;
import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;

import java.util.*;

public class Bag extends StudentHolder {
    Random r;

    /**
     * Standard constructor for bag, initializes it to 130 studentsMax,
     * 26 studentsMaxColor as per the definition of StudentHolder.
     * It also loads 24 student for each color, which correspond to the
     * initial 26 minus the ones needed to initialize Islands.
     */
    public Bag() {
        super(130, 26);
        for(Color color : Color.values()){
            try{
                for(int i = 0; i< 24; i++)
                    addStudent(color);
            }catch (NoSpaceForStudentException e){
                Log.logger.severe("Errors in the making of bag");
            }
        }
        r = new Random();
    }

    /**
     * Custom constructor of bag, loads studentsNumber student for each color
     * @param studentsNumber
     */
    public Bag(int studentsNumber){
        r = new Random();
        for(Color color : Color.values()) {
            try {
                for (int i = 0; i < studentsNumber; i++)
                    addStudent(color);
            } catch (NoSpaceForStudentException e) {
                Log.logger.severe("Errors in the making of bag");
            }
        }
    }

    /**
     * Removes a single random student from the bag, and returns its color
     * @return the extracted student's color, null if the bag was empty
     */
    public Color extractRandomStudent() {
        EnumMap<Color, Integer> copy = this.getAllStudents();
        int total = copy.values().stream().reduce(0, Integer :: sum);
        int randomNumber = r.nextInt(total);
        int bias = 0;
        Color toReturn = null;

        for (Color c : Color.values()) {
            bias += copy.get(c);
            if (randomNumber < bias) {
                toReturn = c;
                try{
                    removeStudent(c);
                }
                catch (NoSuchStudentException e){
                    Log.logger.severe("Bag code is bugged. Either that or random doesn't work");
                }
                break;
            }
        }
        return toReturn;
    }
}

