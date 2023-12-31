package englishtraining.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "WordList not found")
public class WordListNotFoundException extends RuntimeException{
    public WordListNotFoundException(String message) {
        super(message);
    }
}
