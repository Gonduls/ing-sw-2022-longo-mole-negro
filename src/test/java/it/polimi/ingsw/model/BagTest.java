package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    @Test
    void testRandomExtraction(){
    Bag bag = new Bag();
    Color cTemp;

    EnumMap<Color,Integer> students = new EnumMap<>(Color.class);

    for(Color c: Color.values()){
        students.put(c, 0);
    }

    for(int i=0;i<120;i++){
        cTemp = bag.extractRandomStudent();
        students.put(cTemp, students.get(cTemp)+1);
    }

    assertEquals(24,students.get(Color.BLUE));
    assertEquals(24,students.get(Color.PINK));
    assertEquals(24,students.get(Color.RED));
    assertEquals(24,students.get(Color.GREEN));
    assertEquals(24,students.get(Color.YELLOW));

    assertEquals(0, bag.getStudentByColor(Color.BLUE));
    assertEquals(0, bag.getStudentByColor(Color.PINK));
    assertEquals(0, bag.getStudentByColor(Color.RED));
    assertEquals(0, bag.getStudentByColor(Color.GREEN));
    assertEquals(0, bag.getStudentByColor(Color.YELLOW));




    }
}