package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;

public class Cloud extends StudentHolder{
    final int size;
    final Bag bag;

    public Cloud(int size, GameManager gameManager){
        super(size, size);
        this.size = size;
        bag = gameManager.getBag();
    }

    void moveAllStudents(School school) throws NoSpaceForStudentException, NoSuchStudentException {
        for(Color c: Color.values()){
            for(int i = getStudentByColor(c); i>0; i--)
                moveStudentTo(c, school.getStudentsAtEntrance());
        }
    }

    void refill() throws NoSpaceForStudentException{
        for(int i = 0; i< size; i++){
            addStudent(bag.extractRandomStudent());
        }
    }
}
