package ke.co.willythedev.acrolinxspellchecker.exception;

/**
 * Raised when a user is not found in the db.
 */
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
