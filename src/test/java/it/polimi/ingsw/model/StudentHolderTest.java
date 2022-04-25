package it.polimi.ingsw.model;

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
}