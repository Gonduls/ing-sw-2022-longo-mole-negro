package it.polimi.ingsw.exceptions;

/**
 * Thrown to indicate that a student could not be added to a StudentHolder as maximum capacity has been reached already
 */
public class NoSpaceForStudentException extends GameException{

    /**
     * Creates an exception with the given message
     * @param message The exception message
     */
    public NoSpaceForStudentException(String message){
        super(message);
    }
}
