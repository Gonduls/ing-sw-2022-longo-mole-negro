package it.polimi.ingsw.model;

public class School {

    private StudentHolder studentsAtTables;
    private StudentHolder studentsAtEntrance;

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
