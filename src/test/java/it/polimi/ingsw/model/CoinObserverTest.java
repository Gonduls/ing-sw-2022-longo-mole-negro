package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoinObserverTest {

    @Test
    void testUpdate() {
        Player playerBlack = new Player(0,"tizio",false);
        CoinObserver CO = new CoinObserver(playerBlack);

        CO.update();
        assertEquals(1, playerBlack.getCoinsOwned());
    }
}