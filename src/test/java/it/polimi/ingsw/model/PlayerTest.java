package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testPlayerConstructor() {
        Player playerBlack = new Player(0, "tizio", false);
        Player playerWhite = new Player(1, "caio", false);

        assertEquals(8, playerBlack.getTowersLeft());
        assertEquals(8, playerWhite.getTowersLeft());

    }

    @Test
    void testPlayerConstructor_ThreePlayers() {
        Player playerBlack = new Player(0, "tizio", true);
        Player playerWhite = new Player(1, "caio", true);
        Player playerGrey = new Player(2,"sempronio", true);

        assertEquals(6, playerBlack.getTowersLeft());
        assertEquals(6, playerGrey.getTowersLeft());
        assertEquals(6, playerWhite.getTowersLeft());

    }

    @Test
    void testPickCard() {
        Player playerBlack = new Player(0, "tizio", false);
        AssistantCard card = AssistantCard.ONE;
        int cardsOwned = playerBlack.getCardsLeft().size();
        try{playerBlack.pickCard(card);} catch (InvalidParameterException e) {assert false;}

        assert(!playerBlack.getCardsLeft().contains(card));
        assertEquals(cardsOwned - 1, playerBlack.getCardsLeft().size());

        try{playerBlack.pickCard(card);} catch (InvalidParameterException e) {assert true;}

    }

    @Test
    void testRemoveCoins() {
        Player playerBlack = new Player(0, "tizio", false);
        playerBlack.addCoin();
        playerBlack.addCoin();
        playerBlack.addCoin();

        assertEquals(4, playerBlack.getCoinsOwned());

        int coinsNumber = 2;

        try{playerBlack.removeCoins(coinsNumber);} catch (InvalidParameterException e) {assert false;}

        assertEquals(2, playerBlack.getCoinsOwned());

        try{playerBlack.removeCoins(coinsNumber);} catch (InvalidParameterException e) {assert false;}

    }


}