package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Class for Island class
 * It checks unify function, as the StudentHolder functionality is tested with anoter test
 * @author marco mole'
 */
class IslandTest {


    @Test
    void testUnify() {
    Island island1 = new Island();
    Island island2 = new Island();

    island1.setTowerColor(TowerColor.BLACK);
    island2.setTowerColor(TowerColor.BLACK);

    island1.addTower();
    island2.addTower();
    island1.addTower();

   try{ island1.addStudent(Color.BLUE); }
   catch(NoSpaceForStudentException e) {assertTrue(false);}

   try{ island1.addStudent(Color.YELLOW); }
   catch(NoSpaceForStudentException e) {assertTrue(false);}

   try{ island1.addStudent(Color.RED); }
   catch(NoSpaceForStudentException e) {assertTrue(false);}

   try{ island2.addStudent(Color.YELLOW); }
   catch(NoSpaceForStudentException e) {assertTrue(false);}

   try{ island2.addStudent(Color.RED); }
   catch(NoSpaceForStudentException e) {assertTrue(false);}

   try{ island2.addStudent(Color.GREEN); }
   catch(NoSpaceForStudentException e) {assertTrue(false);}


   island1.unify(island2);

   assertEquals(3,island1.getTowerNumber());
   assertEquals(TowerColor.BLACK,island1.getTower());

   assertEquals(2,island1.getStudentByColor(Color.YELLOW));
   assertEquals(2,island1.getStudentByColor(Color.RED));
   assertEquals(1,island1.getStudentByColor(Color.BLUE)); // case in which the second one has 0 students of that color
   assertEquals(0,island1.getStudentByColor(Color.PINK)); // case in which both are at 0
   assertEquals(1,island1.getStudentByColor(Color.GREEN)); // case in which the first one has 0 students of that color




    }
}