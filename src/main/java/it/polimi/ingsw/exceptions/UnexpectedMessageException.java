package it.polimi.ingsw.exceptions;

/**
 * Thrown to indicate that a message that was not supposed to be received at that point was received
 */
public class UnexpectedMessageException extends Exception{

    /**
     * Creates an exception with the given message
     * @param message The exception message
     */
    public UnexpectedMessageException(String message){
        super(message);
    }
}
