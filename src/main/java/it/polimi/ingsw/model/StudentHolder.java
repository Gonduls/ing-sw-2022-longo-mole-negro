package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentHolder {
    HashMap<Color, Integer> students;
    int studentsMax;
    int studentsMaxColor;
    List<StudentHolderObserver> observers = new ArrayList<>();

    public StudentHolder(){
        students = new HashMap<>();
        for(Color color : Color.values())
            students.put(color, 0);
        studentsMax = Integer.MAX_VALUE;
        studentsMaxColor = Integer.MAX_VALUE;

    }

    public StudentHolder(int studentsMax, int studentsMaxColor){
        this();
        this.studentsMax = studentsMax;
        this.studentsMaxColor = studentsMaxColor;
    }

    public HashMap<Color, Integer> getStudents() {
        return new HashMap<>(students);
    }

    void addStudent(Color student) throws NoSpaceForStudentException{
        if(students.values().stream().reduce(0, Integer::sum) == studentsMax)
            throw new NoSpaceForStudentException("No more students can be added to this holder");

        if(students.get(student) == studentsMaxColor)
            throw new NoSpaceForStudentException("No more students of this color can be added to this holder");

        students.put(student, students.get(student) + 1);

    }

    void removeStudent(Color student) throws NoSuchStudentException{
        if(students.get(student) == 0)
            throw new NoSuchStudentException("No student of this color are present");

        students.put(student, students.get(student) - 1);

    }

    //void moveStudentTo


    /*removeStudent(student: Color): void
~ moveStudentTo(student: Color, holder: StudentHolder
~ attach(obs: StudentHolderObserver)
~ detach(obs: StudentHolderObserver)
~ notify()*/
}
