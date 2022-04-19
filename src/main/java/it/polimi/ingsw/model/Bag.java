package it.polimi.ingsw.model;

import java.util.*;

public class Bag extends StudentHolder {
    Random r;

    public Bag() {
        super(130, 26);
        for(Color color : Color.values()){
            try{
                for(int i = 0; i< 24; i++)
                    addStudent(color);
            }catch (NoSpaceForStudentException e){
                System.out.println("Errors in the making of bag");
            }
        }
        r = new Random();
    }

    public Bag(int studentsNumber){
        r = new Random();
        for(Color color : Color.values()) {
            try {
                for (int i = 0; i < studentsNumber; i++)
                    addStudent(color);
            } catch (NoSpaceForStudentException e) {
                System.out.println("Errors in the making of bag");
            }
        }
    }

    public Color extractRandomStudent() {
        EnumMap<Color, Integer> copy = getAllStudents();
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
                    System.out.println("Bag code is bugged. Either that or random doesn't work");
                }
                break;
            }
        }
        return toReturn;

    }


}

