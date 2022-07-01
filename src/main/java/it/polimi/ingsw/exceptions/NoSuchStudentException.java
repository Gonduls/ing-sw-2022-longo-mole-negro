package it.polimi.ingsw.exceptions;

/**
 * Thrown to indicate that a student could not be removed from a StudentHolder
 * as it was not present at the time of removal
 */
public class NoSuchStudentException extends GameException {

    /**
     * Creates an exception with the given message
     * @param message The exception message
     */
    public NoSuchStudentException(String message){
        super(message);
    }
}
