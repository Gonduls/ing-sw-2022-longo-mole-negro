package it.polimi.ingsw.model;

public class StudentHolder {
    int[] students;
    int studentsMax;
    int studentsMaxColor;

    public StudentHolder(){
        students = new int[Color.values().length];
        studentsMax = Integer.MAX_VALUE;
        studentsMaxColor = Integer.MAX_VALUE;

    }

}
