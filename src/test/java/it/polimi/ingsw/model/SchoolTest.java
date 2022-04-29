package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchoolTest {

    @Test
    void testSchoolConstructor() {
        School schoolBlack = new School(false);
        School schoolWhite = new School(false);
        Bag bag = new Bag();
        int sumBlack = 0;
        int sumWhite = 0;

        schoolBlack.initializeGardens(bag, false);
        schoolWhite.initializeGardens(bag, false);


        for(Color color : Color.values()) {
            sumBlack += schoolBlack.getStudentsAtEntrance().getStudentByColor(color);
            sumWhite += schoolWhite.getStudentsAtEntrance().getStudentByColor(color);
        }

        assertEquals(7, sumBlack);
        assertEquals(7, sumWhite);

        try {schoolBlack.addToEntrance(bag.extractRandomStudent());} catch (NoSpaceForStudentException e) {assert true;}
        try {schoolWhite.addToEntrance(bag.extractRandomStudent());} catch (NoSpaceForStudentException e) {assert true;}

        sumBlack = 0;
        sumWhite = 0;
        for(Color color : Color.values()) {
            sumBlack += schoolBlack.getStudentsAtTables().getStudentByColor(color);
            sumWhite += schoolWhite.getStudentsAtTables().getStudentByColor(color);
        }

        assertEquals(0, sumBlack);
        assertEquals(0, sumWhite);

        try {schoolBlack.addToTables(bag.extractRandomStudent());} catch (NoSpaceForStudentException e) {assert false;}
        try {schoolWhite.addToTables(bag.extractRandomStudent());} catch (NoSpaceForStudentException e) {assert false;}



    }

    @Test
    void testSchoolConstructor_ThreePlayers() {
        School schoolBlack = new School(true);
        School schoolWhite = new School(true);
        Bag bag = new Bag();
        int sumBlack = 0;
        int sumWhite = 0;


        schoolBlack.initializeGardens(bag, true);
        schoolWhite.initializeGardens(bag, true);


        for(Color color : Color.values()) {
            sumBlack += schoolBlack.getStudentsAtEntrance().getStudentByColor(color);
            sumWhite += schoolWhite.getStudentsAtEntrance().getStudentByColor(color);
        }

        assertEquals(9, sumBlack);
        assertEquals(9, sumWhite);

        try {schoolBlack.addToEntrance(bag.extractRandomStudent());} catch (NoSpaceForStudentException e) {assert true;}
        try {schoolWhite.addToEntrance(bag.extractRandomStudent());} catch (NoSpaceForStudentException e) {assert true;}


    }

    @Test
    void testInitializeGarden() {
        School schoolBlack = new School(false);
        Bag bag = new Bag();
        int sumBlack = 0;

        schoolBlack.initializeGardens(bag, false);

        for(Color color : Color.values()) {
            sumBlack += schoolBlack.getStudentsAtEntrance().getStudentByColor(color);
        }

        assertEquals(7, sumBlack);

    }

    @Test
    void testInitializeGarden_ThreePlayers() {
        School schoolBlack = new School(true);
        Bag bag = new Bag();
        int sumBlack = 0;

        schoolBlack.initializeGardens(bag, true);

        for(Color color : Color.values()) {
            sumBlack += schoolBlack.getStudentsAtEntrance().getStudentByColor(color);
        }

        assertEquals(9, sumBlack);

    }

    @Test
    void testRemoveFromEntrance() {
        School schoolBlack = new School(false);
        Bag bag = new Bag();
        int sumBlack = 0;
        Color color = Color.RED;

        try{schoolBlack.removeFromEntrance(color);} catch (NoSuchStudentException e) {assert  true;}

        schoolBlack.initializeGardens(bag, false);


        if (schoolBlack.getStudentsAtEntrance().getStudentByColor(color) > 0) {
            try {
                schoolBlack.removeFromEntrance(color);
            } catch (NoSuchStudentException e) {
                assert false;
            }
        }
    }

    @Test
    void testAddToEntrance() {

    }

}