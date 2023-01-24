package ke.co.willythedev.acrolinxspellchecker.exception;

import java.io.IOException;

public class CannotCheckSpellingMistakesException extends IOException {

    public CannotCheckSpellingMistakesException(String message) {
        super(message);
    }

    public CannotCheckSpellingMistakesException(String message, Throwable cause) {
        super(message, cause);
    }
}
