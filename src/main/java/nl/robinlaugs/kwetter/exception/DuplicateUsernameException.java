package nl.robinlaugs.kwetter.exception;

/**
 * @author Robin Laugs
 */
public class DuplicateUsernameException extends Exception {

    public DuplicateUsernameException() {
        super("Username already exists");
    }

}
