package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testMoveMotherNature_simple(){
        Professors prof = new Professors();
        Player playerBlack = new Player(0,"tizio",false);
        Player playerWhite = new Player(1,"caio", false);

        prof.setToPlayer(Color.RED, playerBlack);
        prof.setToPlayer(Color.BLUE, playerWhite);

        Board board = new Board();
        assert (board.getIslands().size() == 12); // starting number of islands
        assert (board.getMotherNaturePosition() == 0); // starting position of MN

        board.moveMotherNature(4);
        assert (board.getMotherNaturePosition() == 4);
        board.moveMotherNature(8);
        assertEquals(0, board.getMotherNaturePosition());
        try {board.moveMotherNature(0);} catch (IllegalArgumentException e) {assert true;}

    }

    @Test
    void testCalculateInfluence_noTowers(){
        Professors prof = new Professors();
        Player playerBlack = new Player(0,"tizio",false);
        Player playerWhite = new Player(1,"caio", false);

        Board board = new Board();

        prof.setToPlayer(Color.BLUE, playerWhite);
        prof.setToPlayer(Color.RED, playerBlack);

       try{ board.getIslands().get(0).addStudent(Color.BLUE);} catch (NoSpaceForStudentException e) {assert false;}
       try{ board.getIslands().get(0).addStudent(Color.BLUE);} catch (NoSpaceForStudentException e) {assert false;}

        assertSame(TowerColor.WHITE, board.calculateInfluence(0, prof));

       try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
       try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
       try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}

        assertSame(TowerColor.BLACK, board.calculateInfluence(0, prof));

       try{ board.getIslands().get(0).removeStudent(Color.RED);} catch (NoSuchStudentException e) {assert false;}

        assertNull(board.calculateInfluence(0, prof));

       try{ board.getIslands().get(0).removeStudent(Color.RED);} catch (NoSuchStudentException e) {assert false;}

        assertSame(TowerColor.WHITE, board.calculateInfluence(0, prof));


    }


    @Test
    void testCalculateInfluence_withTowers(){

        Professors prof = new Professors();
        Player playerBlack = new Player(0,"tizio",false);
        Player playerWhite = new Player(1,"caio", false);

        Board board = new Board();

        prof.setToPlayer(Color.BLUE, playerWhite);
        prof.setToPlayer(Color.RED, playerBlack);

        try{ board.getIslands().get(0).addStudent(Color.BLUE);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.BLUE);} catch (NoSpaceForStudentException e) {assert false;}
        board.getIslands().get(0).addTower();
        board.getIslands().get(0).setTowerColor(TowerColor.WHITE);

        assertSame(TowerColor.WHITE, board.calculateInfluence(0, prof));

        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}

        assertSame(TowerColor.BLACK, board.calculateInfluence(0, prof));

        try{ board.getIslands().get(0).removeStudent(Color.RED);} catch (NoSuchStudentException e) {assert false;}

        assertNull(board.calculateInfluence(0, prof));

        try{ board.getIslands().get(0).removeStudent(Color.RED);} catch (NoSuchStudentException e) {assert false;}

        assertSame(TowerColor.WHITE, board.calculateInfluence(0, prof));
    }

    @Test
    void testMergeIsland_ThreeIslands(){
        Board board = new Board();

        Player playerBlack = new Player(0,"tizio",false);
        Player playerWhite = new Player(1,"caio", false);

        Professors prof = new Professors();

        prof.setToPlayer(Color.BLUE,playerWhite);
        prof.setToPlayer(Color.RED, playerBlack);

        int redStudents1 = board.getIslands().get(1).getStudentByColor(Color.RED);
        int redStudents2 = board.getIslands().get(2).getStudentByColor(Color.RED);

        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        board.getIslands().get(0).setTowerColor(board.calculateInfluence(0,prof));
        assertSame(TowerColor.BLACK, board.getIslands().get(0).getTower());
        board.getIslands().get(0).addTower();


        try{ board.getIslands().get(1).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(1).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(1).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        board.getIslands().get(1).setTowerColor(board.calculateInfluence(1,prof));
        assertSame(TowerColor.BLACK, board.getIslands().get(1).getTower());
        board.getIslands().get(1).addTower();


        try{ board.getIslands().get(2).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(2).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(2).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        board.getIslands().get(2).setTowerColor(board.calculateInfluence(2,prof));
        assertSame(TowerColor.BLACK, board.getIslands().get(2).getTower());
        board.getIslands().get(2).addTower();


        board.mergeIsland(1, null);

        assertEquals(10, board.getIslands().size());
        assertEquals((int) board.getIslands().get(0).getStudentByColor(Color.RED), (9 + redStudents1 + redStudents2));
        assertEquals(3, board.getIslands().get(0).getTowerNumber());
    }

}