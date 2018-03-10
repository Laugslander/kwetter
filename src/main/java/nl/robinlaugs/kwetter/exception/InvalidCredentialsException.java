package nl.robinlaugs.kwetter.exception;

/**
 * @author Robin Laugs
 */
public class InvalidCredentialsException extends Exception {

    public InvalidCredentialsException() {
        super("Username or password is not correct");
    }

}
