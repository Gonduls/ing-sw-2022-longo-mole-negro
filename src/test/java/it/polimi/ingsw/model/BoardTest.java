package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.server.DummyRoom;
import it.polimi.ingsw.server.ModelObserver;
import it.polimi.ingsw.server.Room;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Room dummyRoom;
    ModelObserver modelObserver;
    @BeforeEach
    void init (){
        dummyRoom = new DummyRoom(0,2,true);
        modelObserver = new ModelObserver(dummyRoom);
    }

    @Test
    void testMoveMotherNature_simple(){
        Professors prof = new Professors();
        Player playerBlack = new Player(0,"tizio",false);
        Player playerWhite = new Player(1,"caio", false);

        prof.setToPlayer(Color.RED, playerBlack);
        prof.setToPlayer(Color.BLUE, playerWhite);

        Board board = new Board(modelObserver);
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

        Board board = new Board(modelObserver);

        prof.setToPlayer(Color.BLUE, playerWhite);
        prof.setToPlayer(Color.RED, playerBlack);

       try{ board.getIslands().get(0).addStudent(Color.BLUE);} catch (NoSpaceForStudentException e) {assert false;}
       try{ board.getIslands().get(0).addStudent(Color.BLUE);} catch (NoSpaceForStudentException e) {assert false;}

        assertSame(TowerColor.WHITE, board.calculateInfluenceSmart(0, prof,null));

       try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
       try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
       try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}

        assertSame(TowerColor.BLACK, board.calculateInfluenceSmart(0, prof,null));

       try{ board.getIslands().get(0).removeStudent(Color.RED);} catch (NoSuchStudentException e) {assert false;}

        assertNull(board.calculateInfluenceSmart(0, prof,null));

       try{ board.getIslands().get(0).removeStudent(Color.RED);} catch (NoSuchStudentException e) {assert false;}

        assertSame(TowerColor.WHITE, board.calculateInfluenceSmart(0, prof,null));


    }


    @Test
    void testCalculateInfluence_withTowers(){

        Professors prof = new Professors();
        Player playerBlack = new Player(0,"tizio",false);
        Player playerWhite = new Player(1,"caio", false);

        Board board = new Board(modelObserver);

        prof.setToPlayer(Color.BLUE, playerWhite);
        prof.setToPlayer(Color.RED, playerBlack);

        try{ board.getIslands().get(0).addStudent(Color.BLUE);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.BLUE);} catch (NoSpaceForStudentException e) {assert false;}
        board.getIslands().get(0).addTower();
        board.getIslands().get(0).setTowerColor(TowerColor.WHITE);

        assertSame(TowerColor.WHITE, board.calculateInfluenceSmart(0, prof,null));

        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}

        assertSame(TowerColor.BLACK, board.calculateInfluenceSmart(0, prof,null));

        try{ board.getIslands().get(0).removeStudent(Color.RED);} catch (NoSuchStudentException e) {assert false;}

        assertNull(board.calculateInfluenceSmart(0, prof,null));

        try{ board.getIslands().get(0).removeStudent(Color.RED);} catch (NoSuchStudentException e) {assert false;}

        assertSame(TowerColor.WHITE, board.calculateInfluenceSmart(0, prof,null));
    }

    @Test
    void testMergeIsland_ThreeIslands(){
        Board board = new Board(modelObserver);

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
        board.getIslands().get(0).setTowerColor(board.calculateInfluenceSmart(0,prof,null));
        assertSame(TowerColor.BLACK, board.getIslands().get(0).getTower());
        board.getIslands().get(0).addTower();


        try{ board.getIslands().get(1).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(1).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(1).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        board.getIslands().get(1).setTowerColor(board.calculateInfluenceSmart(1,prof,null));
        assertSame(TowerColor.BLACK, board.getIslands().get(1).getTower());
        board.getIslands().get(1).addTower();


        try{ board.getIslands().get(2).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(2).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(2).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        board.getIslands().get(2).setTowerColor(board.calculateInfluenceSmart(2,prof,null));
        assertSame(TowerColor.BLACK, board.getIslands().get(2).getTower());
        board.getIslands().get(2).addTower();


        board.mergeIsland(1, null);

        assertEquals(10, board.getIslands().size());
        assertEquals((int) board.getIslands().get(0).getStudentByColor(Color.RED), (9 + redStudents1 + redStudents2));
        assertEquals(3, board.getIslands().get(0).getTowerNumber());
    }

    @Test
    void testInfluenceCardNine(){ //card nine i.e. towers don't count torwards influence
        CharacterCardNine cardNine = new CharacterCardNine(modelObserver);
        Professors prof = new Professors();
        Player playerBlack = new Player(0,"tizio",false);
        Player playerWhite = new Player(1,"caio", false);

        Board board = new Board(modelObserver);


        prof.setToPlayer(Color.RED, playerBlack);
        prof.setToPlayer(Color.BLUE, playerWhite);


        try{ board.getIslands().get(0).addStudent(Color.BLUE);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.BLUE);} catch (NoSpaceForStudentException e) {assert false;}
        board.getIslands().get(0).addTower();
        board.getIslands().get(0).setTowerColor(TowerColor.WHITE);

        assertSame(TowerColor.WHITE, board.calculateInfluenceSmart(0, prof,cardNine));

        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}

        assertSame(TowerColor.BLACK, board.calculateInfluenceSmart(0, prof,cardNine));

        try{ board.getIslands().get(0).removeStudent(Color.RED);} catch (NoSuchStudentException e) {assert false;}
         // 3 RED(playerBlack), 2 BLUE (playerWhite) 1 tower White (NB towers don't count)
        assertSame(TowerColor.BLACK, board.calculateInfluenceSmart(0, prof,cardNine));

        try{ board.getIslands().get(0).removeStudent(Color.RED);} catch (NoSuchStudentException e) {assert false;}
        // 2 RED(playerBlack), 2 BLUE (playerWhite) 1 tower White (NB towers don't count)
        assertNull( board.calculateInfluenceSmart(0, prof,cardNine));
    }
    @Test
    void testInfluenceCardSix(){ //the player referenced the card has a +2 bonus in the influence calc

        CharacterCardSix cardSix = new CharacterCardSix(modelObserver);

        Professors prof = new Professors();
        Player playerBlack = new Player(0,"tizio",false);
        Player playerWhite = new Player(1,"caio", false);

        cardSix.setPlayer(playerBlack);

        Board board = new Board(modelObserver);


        prof.setToPlayer(Color.RED, playerBlack);
        prof.setToPlayer(Color.BLUE, playerWhite);


        try{ board.getIslands().get(0).addStudent(Color.BLUE);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.BLUE);} catch (NoSpaceForStudentException e) {assert false;}
        board.getIslands().get(0).addTower();
        board.getIslands().get(0).setTowerColor(TowerColor.WHITE);
        // 2 BLUE (white) 1 tower white (black has +2)
        assertSame(TowerColor.WHITE, board.calculateInfluenceSmart(0, prof,cardSix));

        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}

        // 2 BLUE (white) 1 RED(black) 1 tower white (black has +2)
        assertNull(board.calculateInfluenceSmart(0,prof,cardSix));

        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}

        // 2 BLUE (white) 2 RED(black) 1 tower white (black has +2)

        assertSame(TowerColor.BLACK, board.calculateInfluenceSmart(0, prof,cardSix));

        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}

        // 2 BLUE (white) 4 RED(black) 1 tower white (black has +2)
        assertSame(TowerColor.BLACK, board.calculateInfluenceSmart(0, prof,cardSix));

        try{ board.getIslands().get(0).removeStudent(Color.RED);} catch (NoSuchStudentException e) {assert false;}
        // 2 BLUE (white) 3 RED(black) 1 tower white (black has +2)
        assertSame(TowerColor.BLACK, board.calculateInfluenceSmart(0, prof,cardSix));

        try{ board.getIslands().get(0).removeStudent(Color.RED);} catch (NoSuchStudentException e) {assert false;}
        // 2 BLUE (white) 2 RED(black) 1 tower white (black has +2)
        assertSame(TowerColor.BLACK, board.calculateInfluenceSmart(0, prof,cardSix));

    }


    @Test
    void testInfluenceCardTen (){ // the chosen color doesn't count for the influence
        CharacterCardTen cardTen = new CharacterCardTen(modelObserver);
        cardTen.setColor(Color.RED);

        Professors prof = new Professors();
        Player playerBlack = new Player(0,"tizio",false);
        Player playerWhite = new Player(1,"caio", false);


        Board board = new Board(modelObserver);


        prof.setToPlayer(Color.RED, playerBlack);
        prof.setToPlayer(Color.GREEN, playerBlack);
        prof.setToPlayer(Color.BLUE, playerWhite);


        try{ board.getIslands().get(0).addStudent(Color.BLUE);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.BLUE);} catch (NoSpaceForStudentException e) {assert false;}
        board.getIslands().get(0).addTower();
        board.getIslands().get(0).setTowerColor(TowerColor.WHITE);

        // 2 BLUE (white) 1 tower white (RED doesn't count)
        assertSame(TowerColor.WHITE, board.calculateInfluenceSmart(0, prof,cardTen));

        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}

        // 2 BLUE (white) 1 RED(black) 1 tower white  (RED doesn't count)
        assertSame(TowerColor.WHITE, board.calculateInfluenceSmart(0, prof,cardTen));

        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}

        // 2 BLUE (white) 2 RED(black) 1 tower white  (RED doesn't count)
        assertSame(TowerColor.WHITE, board.calculateInfluenceSmart(0, prof,cardTen));

        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.RED);} catch (NoSpaceForStudentException e) {assert false;}

        // 2 BLUE (white) 4 RED(black) 1 tower white (RED doesn't count)
        assertSame(TowerColor.WHITE, board.calculateInfluenceSmart(0, prof,cardTen));

        try{ board.getIslands().get(0).removeStudent(Color.RED);} catch (NoSuchStudentException e) {assert false;}
        // 2 BLUE (white) 3 RED(black) 1 tower white (RED doesn't count)
        assertSame(TowerColor.WHITE, board.calculateInfluenceSmart(0, prof,cardTen));

        try{ board.getIslands().get(0).removeStudent(Color.RED);} catch (NoSuchStudentException e) {assert false;}
        // 2 BLUE (white) 2 RED(black) 1 tower white (RED doesn't count)
        assertSame(TowerColor.WHITE, board.calculateInfluenceSmart(0, prof,cardTen));

        try{ board.getIslands().get(0).addStudent(Color.GREEN);} catch (NoSpaceForStudentException e) {assert false;}
        try{ board.getIslands().get(0).addStudent(Color.GREEN);} catch (NoSpaceForStudentException e) {assert false;}
        assertSame(TowerColor.WHITE, board.calculateInfluenceSmart(0, prof,cardTen));
        try{ board.getIslands().get(0).addStudent(Color.GREEN);} catch (NoSpaceForStudentException e) {assert false;}
        assertSame(null, board.calculateInfluenceSmart(0, prof,cardTen));
        try{ board.getIslands().get(0).addStudent(Color.GREEN);} catch (NoSpaceForStudentException e) {assert false;}
        assertSame(TowerColor.BLACK, board.calculateInfluenceSmart(0, prof,cardTen));

    }


}