package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorsTest {

    @Test
    void testProfessorsConstructor() {
        Professors professors = new Professors();

        for(Color color : Color.values())
            assertNull(professors.getOwners().get(color));

    }

    @Test
    void testSetToPlayer() {
        Player playerBlack = new Player(0, "tizio", false);
        Professors professors = new Professors();
        Professors professors1 = new Professors();

        professors.setToPlayer(Color.RED, playerBlack);

        assert(professors.getOwners().get(Color.RED) == playerBlack);
        assert(professors.getOwners().get(Color.BLUE) == null);


    }

}