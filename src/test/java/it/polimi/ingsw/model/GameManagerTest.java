package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {
    @Test
    void testEmptyCloudInPlayer() {
        Player[] players = new Player[2];
        GameManager game = new GameManager(players, false);
        game.getPlayers()[0] = new Player(0,"tizio", false);
        int sum = 0;

        try{game.emptyCloudInPlayer(1, players[0]);} catch (NoSpaceForStudentException | NoSuchStudentException e) {assert false;}

        for(Color color : Color.values()) {
            sum += game.getPlayers()[0].getSchool().getStudentsAtEntrance().getStudentByColor(color);
        }

        assertEquals(7, sum);


    }

    @Test
    void testRefillClouds() {
        Player[] players = new Player[2];
        GameManager game = new GameManager(players, false);
    }

}