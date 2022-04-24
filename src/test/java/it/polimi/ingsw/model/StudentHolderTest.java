package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
                assert(false);
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
                    System.out.println("i:" +i );
                    assert(i == 20);

                }
            }
        }
    }
}