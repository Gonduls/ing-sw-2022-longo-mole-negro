package it.polimi.ingsw.exceptions;

/**
 * Thrown to indicate an error in the Controller. The error could be also an illegal move.
 */
public class GameException extends Exception{

    /**
     * Creates an exception with the given message
     * @param message The exception message
     */
    public GameException(String message) {
        super(message);
    }
}
