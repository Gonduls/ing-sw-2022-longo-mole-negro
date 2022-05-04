package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
Test for the StudentHolder class

 @author marco mole'
*/
class StudentHolderTest {

    @Test
    void testAddStudent_maxLimit(){
        StudentHolder sh = new StudentHolder();

        for(int i =0; i<50;i++) {
            for (Color c : Color.values()) {
                assertEquals(i, sh.getStudentByColor(c));
                try {
                    sh.addStudent(c);
                } catch (NoSpaceForStudentException ignored) {
                    fail();
                }
            }
        }
    }

    //every loop a student for each color is added
    @Test
    void testAddStudent_withLimit(){
        StudentHolder sh = new StudentHolder(100,20);

        for(int i =0; i<21;i++) {
            for (Color c : Color.values()) {
                assertEquals(i, sh.getStudentByColor(c));
                try {
                    sh.addStudent(c);
                } catch (NoSpaceForStudentException e) {
                    assertEquals(20, i);
                }
            }
        }
    }


    /**
     * remove students one by one, check that has worked
     * then for ten times try to remove students from an empty sh and check that the exception has been effectively
     * been raised
     */
    @Test
    void testRemoveStudent(){

        StudentHolder sh = new StudentHolder();
        //populate the sh with 500 students, 100 for each color
        for (int i = 0; i <100; i++){
            for(Color c: Color.values()){
                try{sh.addStudent(c);} catch(NoSpaceForStudentException ignored){}
            }
        }

        for(int i =0;i<110;i++){
            for(Color c: Color.values()){
                try{sh.removeStudent(c);} catch(NoSuchStudentException e) {assert i >= 100;}
                finally {
                    assertEquals(i<100?99-i:0, sh.getStudentByColor(c));
                }
            }
        }
    }

    @Test
    void testMoveStudentTo() {
        StudentHolder one = new StudentHolder(5,1);
        StudentHolder two = new StudentHolder(5,1);
        int sum = 0;

        for(Color color: Color.values()) {
            try{one.addStudent(color);} catch (NoSpaceForStudentException e) {assert false;}
            try{two.addStudent(color);} catch (NoSpaceForStudentException e) {assert false;}
        }

        try{one.moveStudentTo(Color.RED, two);} catch (NoSpaceForStudentException | NoSuchStudentException e) {assert true;}

        for(Color color : Color.values())
            sum += one.getStudentByColor(color);

        assertEquals(5, sum);

        try{one.removeStudent(Color.RED);} catch (NoSuchStudentException e) {assert false;}
        try{one.moveStudentTo(Color.RED, two);} catch (NoSpaceForStudentException | NoSuchStudentException e) {assert true;}

        try{two.moveStudentTo(Color.RED, one);} catch (NoSpaceForStudentException | NoSuchStudentException e) {assert false;}

        sum = 0;
        for(Color color : Color.values())
            sum += one.getStudentByColor(color);

        assertEquals(5, sum);

        sum = 0;
        for(Color color : Color.values())
            sum += two.getStudentByColor(color);

        assertEquals(4, sum);

    }
}