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

    void removeFromEntrance(Color student) throws noSuchStudentException {
        studentsAtEntrance.removeStudent(student);
    }

    void addToEntrance(Color student) throws noSpaceForStudentException {
        studentsAtEntrance.addStudent(student);
    }

    void removeFromTables(Color student) throws noSuchStudentException {
        studentsAtTables.removeStudent(student);
    }

    void addToTables(Color student) throws noSpaceForStudentException {
        studentsAtTables.addStudent(student);
    }
}
