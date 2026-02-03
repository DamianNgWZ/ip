package dbot.exception;

/**
 * Custom exception class for the Dbot application.
 * Thrown when there are errors in parsing user input or executing commands.
 */
public class DbotException extends Exception {
    public DbotException(String message) {
        super(message);
    }
}
