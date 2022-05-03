package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {
    @Test
    void testEmptyCloudInPlayer() {

        //TODO correggi come sotto
        Player[] players = new Player[]{new Player(0, "tizio", false), new Player(1, "caio", false)};
        GameManager game = new GameManager(players, false);
        int sum = 0;
        Color color1 = Color.RED;
        Color color2 = Color.GREEN;
        Color color3 = Color.BLUE;
        game.refillClouds();
        try{game.getPlayers()[0].getSchool().removeFromEntrance(color1);} catch (NoSuchStudentException e) {assert true;}
        try{game.getPlayers()[0].getSchool().removeFromEntrance(color2);} catch (NoSuchStudentException e) {assert true;}
        try{game.getPlayers()[0].getSchool().removeFromEntrance(color3);} catch (NoSuchStudentException e) {assert true;}


        try{game.emptyCloudInPlayer(1, players[0]);} catch (NoSpaceForStudentException e) {assert false;} catch (NoSuchStudentException e1) {assert false;}
        try{game.emptyCloudInPlayer(1, players[0]);} catch (NoSpaceForStudentException e) {assert true;} catch (NoSuchStudentException e1) {assert true;}

        for(Color color : Color.values()) {
            sum += game.getPlayers()[0].getSchool().getStudentsAtEntrance().getStudentByColor(color);
        }

        assertEquals(7, sum);

    }

    @Test
    void testMoveMotherNature() {
        //TODO

    }

    @Test
    void testMoveStudentFromEntranceToIsland() {
        //TODO
        Player[] players = new Player[]{new Player(0, "tizio", false), new Player(1, "caio", false)};

        GameManager game = new GameManager(players, false);
        if(game.getPlayers()[0].getSchool().getStudentsAtEntrance().getStudentByColor(Color.RED) == 0) {
            try {
                game.moveStudentFromEntranceToIsland(players[0].getSchool(), Color.RED, 1);
            } catch (IllegalArgumentException | NoSuchStudentException e) {
                assert true;
            }
        } else {
            try {
                game.moveStudentFromEntranceToIsland(players[0].getSchool(), Color.RED, 1);
            } catch (IllegalArgumentException | NoSuchStudentException e) {
                assert false;
            }
            try {
                game.moveStudentFromEntranceToIsland(players[0].getSchool(), Color.RED, -1);
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
        //TODO
        Player[] players = new Player[]{new Player(0, "tizio", false), new Player(1, "caio", false)};
        GameManager game = new GameManager(players, false);
        int sumEntrance = 0;
        int sumTables = 0;

        if(game.getPlayers()[0].getSchool().getStudentsAtEntrance().getStudentByColor(Color.RED) == 0) {
            System.out.println("ok");
            try{game.moveStudentFromEntranceToTable(players[0].getSchool(), Color.RED);} catch (NoSuchStudentException | NoSpaceForStudentException e) {assert true;}
            for(Color color : Color.values()) {
                sumEntrance += game.getPlayers()[0].getSchool().getStudentsAtEntrance().getStudentByColor(color);
                sumTables += game.getPlayers()[0].getSchool().getStudentsAtTables().getStudentByColor(color);

            }

            assertEquals(7, sumEntrance);
            assertEquals(0, sumTables);
        } else {
            try{game.moveStudentFromEntranceToTable(players[0].getSchool(), Color.RED);} catch (NoSuchStudentException | NoSpaceForStudentException e) {assert false;}

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