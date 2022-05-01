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
        int sumBlackPrev = 0;
        int sumBlackPost = 0;
        Color color = Color.RED;

        schoolBlack.initializeGardens(bag, false);

        for(Color color1 : Color.values()) {
            sumBlackPrev += schoolBlack.getStudentsAtEntrance().getStudentByColor(color1);
        }

        assertEquals(7, sumBlackPrev);

        if (schoolBlack.getStudentsAtEntrance().getStudentByColor(color) > 0) {
            try {schoolBlack.removeFromEntrance(color);} catch (NoSuchStudentException e) {assert false;}
            for(Color color2 : Color.values()) {
                sumBlackPost += schoolBlack.getStudentsAtEntrance().getStudentByColor(color2);
            }
            assertEquals(sumBlackPrev - 1, sumBlackPost);
        } else {
            try {schoolBlack.removeFromEntrance(color);} catch (NoSuchStudentException e) {assert true;}
            for(Color color2 : Color.values()) {
                sumBlackPost += schoolBlack.getStudentsAtEntrance().getStudentByColor(color2);
            }
            assertEquals(sumBlackPrev, sumBlackPost);
        }

    }

    @Test
    void testAddToEntrance() {
        School schoolBlack = new School(false);
        int sumBlackPrev = 0;
        int sumBlackPost = 0;
        Color color = Color.RED;

        for(int i = 0; i < 7; i++) {
            try{schoolBlack.addToEntrance(color);} catch (NoSpaceForStudentException e) {assert false;}
        }

        try {schoolBlack.removeFromEntrance(color);} catch (NoSuchStudentException e) {assert false;}
        try {schoolBlack.removeFromEntrance(color);} catch (NoSuchStudentException e) {assert false;}

        for(Color color1 : Color.values()) {
            sumBlackPrev += schoolBlack.getStudentsAtEntrance().getStudentByColor(color1);
        }

        assertEquals(5, sumBlackPrev);

        try {schoolBlack.addToEntrance(color);} catch (NoSpaceForStudentException e) {assert false;}
        try {schoolBlack.addToEntrance(color);} catch (NoSpaceForStudentException e) {assert false;}
        try {schoolBlack.addToEntrance(color);} catch (NoSpaceForStudentException e) {assert true;}


        for(Color color2 : Color.values()) {
            sumBlackPost += schoolBlack.getStudentsAtEntrance().getStudentByColor(color2);
        }
        assertEquals(sumBlackPrev + 2, sumBlackPost);



    }

    @Test
    void testRemoveFromTables() {
        School schoolBlack = new School(false);
        Bag bag = new Bag();
        int sumBlackPrev = 0;
        int sumBlackPost = 0;
        Color color = Color.RED;
        Color colorG = Color.GREEN;

        schoolBlack.initializeGardens(bag, false);

        for(Color color1 : Color.values()) {
            sumBlackPrev += schoolBlack.getStudentsAtTables().getStudentByColor(color1);
        }

        assertEquals(0, sumBlackPrev);

        try{schoolBlack.addToTables(color);} catch (NoSpaceForStudentException e) {assert false;}
        try{schoolBlack.addToTables(color);} catch (NoSpaceForStudentException e) {assert false;}
        try{schoolBlack.addToTables(color);} catch (NoSpaceForStudentException e) {assert false;}

        for(Color color1 : Color.values()) {
            sumBlackPrev += schoolBlack.getStudentsAtTables().getStudentByColor(color1);
        }

        assertEquals(3, sumBlackPrev);

        try{schoolBlack.removeFromTables(color);} catch (NoSuchStudentException e) {assert false;}
        try{schoolBlack.removeFromTables(colorG);} catch (NoSuchStudentException e) {assert true;}

        for(Color color2 : Color.values()) {
            sumBlackPost += schoolBlack.getStudentsAtTables().getStudentByColor(color2);
        }

        assertEquals(sumBlackPrev - 1, sumBlackPost);

    }

    @Test
    void testAddToTables() {
        School schoolBlack = new School(false);
        Bag bag = new Bag();
        int sumBlackPrev = 0;
        int sumBlackPost = 0;
        Color color = Color.RED;

        schoolBlack.initializeGardens(bag, false);

        for(Color color1 : Color.values()) {
            sumBlackPrev += schoolBlack.getStudentsAtTables().getStudentByColor(color1);
        }

        assertEquals(0, sumBlackPrev);

        for(int i = 0; i < 10; i++) {
            try{schoolBlack.addToTables(color);} catch (NoSpaceForStudentException e) {assert false;}
        }

        for(Color color1 : Color.values()) {
            sumBlackPrev += schoolBlack.getStudentsAtTables().getStudentByColor(color1);
        }

        assertEquals(10, sumBlackPrev);


        try{schoolBlack.addToTables(color);} catch (NoSpaceForStudentException e) {assert true;}

    }

}

