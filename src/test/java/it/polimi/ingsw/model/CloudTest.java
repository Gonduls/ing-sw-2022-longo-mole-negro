package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {




    @Test
    void testMoveAllStudents() {

        Player playerBlack = new Player(0,"tizio",false);
        Bag bag = new Bag();
        Cloud cloud = new Cloud(3, bag);

        try{cloud.refill();} catch (NoSpaceForStudentException e) {assert false;}

        try {cloud.moveAllStudents(playerBlack);} catch (NoSpaceForStudentException | NoSuchStudentException e) {assert false;}

        int sum = 0;
        for (Color color : Color.values()) {
            for (int i = cloud.getStudentByColor(color); i > 0; i--) {
                sum++;
            }
        }

        assertEquals(0, sum);

    }

    @Test
    void testRefill() {

        Bag bag = new Bag();
        Cloud cloud = new Cloud(3, bag);

        try{cloud.refill();} catch (NoSpaceForStudentException e) {assert false;}

        int sum = 0;

        for (Color color : Color.values()) {
            for (int i = cloud.getStudentByColor(color); i > 0; i--) {
                sum++;
            }
        }

        assertEquals(3 , sum);


    }

}