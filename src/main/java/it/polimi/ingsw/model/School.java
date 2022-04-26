package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;

public class School {

    private StudentHolder studentsAtTables;
    private StudentHolder studentsAtEntrance;

    public School(boolean threePlayers){
        if(threePlayers){
            studentsAtEntrance = new StudentHolder(9, 9);
        }
        else{
            studentsAtEntrance = new StudentHolder(7, 7);
        }

        studentsAtTables = new StudentHolder(10 * 5, 10);
    }

    void initializeGardens(Bag bag, boolean threePlayers){
        for(int i = 0; i< 7; i++){
            try{
                studentsAtEntrance.addStudent(bag.extractRandomStudent());
            } catch(NoSpaceForStudentException e){
                System.out.println("Someone tried to initialize garden while already initialized");
            }
        }

        if(threePlayers){
            try{
                studentsAtEntrance.addStudent(bag.extractRandomStudent());
                studentsAtEntrance.addStudent(bag.extractRandomStudent());
            } catch(NoSpaceForStudentException e){
                System.out.println("Someone tried to initialize garden while already initialized");
            }
        }
    }

    StudentHolder getStudentsAtTables() {
        return studentsAtTables;
    }

    StudentHolder getStudentsAtEntrance() {
        return studentsAtEntrance;
    }

    void removeFromEntrance(Color student) throws NoSuchStudentException {
        studentsAtEntrance.removeStudent(student);
    }

    void addToEntrance(Color student) throws NoSpaceForStudentException {
        studentsAtEntrance.addStudent(student);
    }

    void removeFromTables(Color student) throws NoSuchStudentException {
        studentsAtTables.removeStudent(student);
    }

    void addToTables(Color student) throws NoSpaceForStudentException {
        studentsAtTables.addStudent(student);
    }
}
