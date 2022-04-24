package it.polimi.ingsw.model;

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
        assert (board.getMotherNaturePosition() == 0);
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

       assert(board.calculateInfluence(0,prof) == TowerColor.WHITE);

       try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
       try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
       try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}

       assert(board.calculateInfluence(0,prof) == TowerColor.BLACK);

       try{ board.getIslands().get(0).removeStudent(Color.RED);} catch (NoSuchStudentException e) {assert false;}

       assert(board.calculateInfluence(0,prof) == null);

       try{ board.getIslands().get(0).removeStudent(Color.RED);} catch (NoSuchStudentException e) {assert false;}

       assert(board.calculateInfluence(0,prof) == TowerColor.WHITE);


    }


    @Test
    void testCalculateInfluece_withTowers(){

        //TODO
    }

    @Test
    void testMergeIsland_ThreeIslands(){
        Board board = new Board();

        Player playerBlack = new Player(0,"tizio",false);
        Player playerWhite = new Player(1,"caio", false);

        Professors prof = new Professors();

        prof.setToPlayer(Color.BLUE,playerWhite);
        prof.setToPlayer(Color.RED, playerBlack);


        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        board.getIslands().get(0).setTowerColor(board.calculateInfluence(0,prof));

        try{ board.getIslands().get(1).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(1).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(1).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        board.getIslands().get(1).setTowerColor(board.calculateInfluence(1,prof));


        try{ board.getIslands().get(2).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(2).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(2).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        board.getIslands().get(2).setTowerColor(board.calculateInfluence(2,prof));


        board.mergeIsland(1);

        assert(board.getIslands().size() == 10);
        assert(board.getIslands().get(0).getStudentByColor(Color.RED) == 9);

        for(int i =0; i<board.getNumberOfIslands(); i++){
            // PERCHÉ LE ALTRE ISOLE HANNO DEGLI STUDENTI???
            System.out.println( i + " " + board.getIslands().get(i));
        }

    }

}