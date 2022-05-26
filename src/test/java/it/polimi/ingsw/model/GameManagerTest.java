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

class GameManagerTest {
    Room dummyRoom;
    ModelObserver modelObserver;
    @BeforeEach
    void  init(){
        dummyRoom = new DummyRoom(0,2,false);
        modelObserver = new ModelObserver(dummyRoom);
    }

    @Test
    void testEmptyCloudInPlayer() {

        Player[] players = new Player[]{new Player(0, "tizio", false), new Player(1, "caio", false)};
        GameManager game = new GameManager(players, false,modelObserver);
        int sum = 0;

        try{game.emptyCloudInPlayer(1, players[0]);} catch (NoSpaceForStudentException e) {assert false;} catch (NoSuchStudentException e1) {assert true;}

        game.refillClouds();


        for(Color color : Color.values()) {
            while(game.getPlayers()[0].getSchool().getStudentsAtEntrance().getStudentByColor(color) != 0)
                try {game.getPlayers()[0].getSchool().removeFromEntrance(color);} catch (NoSuchStudentException e) {assert true;}
        }

        try{game.emptyCloudInPlayer(0, players[0]);} catch (NoSpaceForStudentException | NoSuchStudentException e) {assert false;}
        try{game.emptyCloudInPlayer(1, players[0]);} catch (NoSpaceForStudentException | NoSuchStudentException e) {assert false;}
        try{game.emptyCloudInPlayer(0, players[0]);} catch (NoSpaceForStudentException e) {assert false;} catch (NoSuchStudentException e1) {assert true;}
        for(Color color : Color.values()) {
            sum += game.getPlayers()[0].getSchool().getStudentsAtEntrance().getStudentByColor(color);
        }

        assertEquals(6, sum);

    }

    @Test
    void testMoveMotherNature() {
        Player[] players = new Player[]{new Player(0, "tizio", false), new Player(1, "caio", false)};
        GameManager game = new GameManager(players, false,modelObserver);

        Color savedColor = Color.RED;

        for(Color color : Color.values()) {
            if(game.getIslands().get(2).getStudentByColor(color) != 0) {
                game.getProfessors().setToPlayer(color, game.getPlayers()[0]);
                savedColor = color;
            }
        }

        assertNull(game.getIslands().get(2).getTower());

        try{game.moveMotherNature(0);} catch (IllegalArgumentException e) {assert true;}
        try{game.moveMotherNature(2);} catch (IllegalArgumentException e) {assert false;}

        assertEquals(2,game.getMotherNaturePosition());
        assertEquals(TowerColor.BLACK, game.getIslands().get(2).getTower());
        assertEquals(1, game.getIslands().get(2).getTowerNumber());
        assertEquals(7,game.getPlayers()[0].getTowersLeft());

        if(game.getPlayers()[1].getSchool().getStudentsAtEntrance().getStudentByColor(Color.BLUE) != 0)
            try{game.moveStudentFromEntranceToIsland(game.getPlayers()[1],Color.BLUE, 2);} catch (NoSuchStudentException | IllegalArgumentException e) {assert false;}
        else {
            try{
                game.getIslands().get(2).addStudent(Color.BLUE);
            } catch (NoSpaceForStudentException e){
                fail();
            }
        }

        game.getProfessors().setToPlayer(Color.BLUE, game.getPlayers()[1]);
        game.getProfessors().setToPlayer(savedColor, game.getPlayers()[1]);

        try{game.moveMotherNature(12);} catch (IllegalArgumentException e) {assert false;}

        assertEquals(2,game.getMotherNaturePosition());
        assertEquals(TowerColor.WHITE, game.getIslands().get(2).getTower());
        assertEquals(1, game.getIslands().get(2).getTowerNumber());
        assertEquals(8,game.getPlayers()[0].getTowersLeft());
        assertEquals(7,game.getPlayers()[1].getTowersLeft());

    }

    @Test
    void testMoveStudentFromEntranceToIsland() {
        Player[] players = new Player[]{new Player(0, "tizio", false), new Player(1, "caio", false)};

        GameManager game = new GameManager(players, false,modelObserver);

        if(game.getPlayers()[0].getSchool().getStudentsAtEntrance().getStudentByColor(Color.RED) == 0) {
            try {
                game.moveStudentFromEntranceToIsland(players[0], Color.RED, 1);
            } catch (IllegalArgumentException | NoSuchStudentException e) {
                assert true;
            }
        } else {
            try {
                game.moveStudentFromEntranceToIsland(players[0], Color.RED, 1);
            } catch (IllegalArgumentException | NoSuchStudentException e) {
                assert false;
            }
            try {
                game.moveStudentFromEntranceToIsland(players[0], Color.RED, -1);
            } catch (IllegalArgumentException | NoSuchStudentException e) {
                assert true;
            }

            int sum = 0;
            for (Color color : Color.values())
                sum += game.getIslands().get(1).getStudentByColor(color);

            assertEquals(2, sum);
        }
    }

    @Test
    void testMoveStudentFromEntranceToTable() {
        Player[] players = new Player[]{new Player(0, "tizio", false), new Player(1, "caio", false)};
        GameManager game = new GameManager(players, false,modelObserver);

        int sumEntrance = 0;
        int sumTables = 0;

        if(game.getPlayers()[0].getSchool().getStudentsAtEntrance().getStudentByColor(Color.RED) == 0) {
            try{game.moveStudentFromEntranceToTable(players[0], Color.RED);} catch (NoSuchStudentException | NoSpaceForStudentException e) {assert true;}
            for(Color color : Color.values()) {
                sumEntrance += game.getPlayers()[0].getSchool().getStudentsAtEntrance().getStudentByColor(color);
                sumTables += game.getPlayers()[0].getSchool().getStudentsAtTables().getStudentByColor(color);

            }

            assertEquals(7, sumEntrance);
            assertEquals(0, sumTables);
        } else {
            try{game.moveStudentFromEntranceToTable(players[0], Color.RED);} catch (NoSuchStudentException | NoSpaceForStudentException e) {assert false;}

            for(Color color : Color.values()) {
                sumEntrance += game.getPlayers()[0].getSchool().getStudentsAtEntrance().getStudentByColor(color);
                sumTables += game.getPlayers()[0].getSchool().getStudentsAtTables().getStudentByColor(color);

            }

            assertEquals(6, sumEntrance);
            assertEquals(1, sumTables);
        }


    }

    @Test
    void testCheckEndConditions() {
        //TODO

    }

}