package it.polimi.ingsw.exceptions;


/**
 * Thrown to indicate an error in the Controller. The error could be also an illegal move.
 */
public class GameException extends Exception{

    public GameException(String message) {
        super(message);
    }
}
