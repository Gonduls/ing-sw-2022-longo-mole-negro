package it.polimi.ingsw.client.view.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardStatusTest {
    private final BoardStatus bs = new BoardStatus(4, true);


    @Test
    void merge() {
        bs.merge(0);
        assertEquals(11, bs.getIslandsCoords().size());
        bs.merge(0);
        assertEquals(10, bs.getIslandsCoords().size());
        bs.merge(0);
        assertEquals(9, bs.getIslandsCoords().size());
        bs.merge(0);
        assertEquals(8, bs.getIslandsCoords().size());
        bs.merge(0);
        assertEquals(7, bs.getIslandsCoords().size());
    }
}