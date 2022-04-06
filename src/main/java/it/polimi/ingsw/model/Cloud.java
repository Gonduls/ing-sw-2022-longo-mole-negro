package it.polimi.ingsw.model;

public class Cloud extends StudentHolder{

    void moveAllStudents(School school) throws NoSpaceForStudentException, NoSuchStudentException{

        for(Color c: Color.values()){

            moveStudentTo(c, school.getStudentsAtEntrance());
        }

    }
}
