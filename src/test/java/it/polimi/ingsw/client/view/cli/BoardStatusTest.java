package it.polimi.ingsw.client.view.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardStatusTest {
    private final BoardStatus bs = new BoardStatus(4, true);


    @Test
    void merge() {
        bs.merge();
        assertEquals(11, bs.getIslands().size());
        bs.merge();
        assertEquals(10, bs.getIslands().size());
        bs.merge();
        assertEquals(9, bs.getIslands().size());
        bs.merge();
        assertEquals(8, bs.getIslands().size());
        bs.merge();
        assertEquals(7, bs.getIslands().size());
    }
}