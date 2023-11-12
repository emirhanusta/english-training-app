package englishtraining.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "WordList not found")
public class WordListNotFoundException extends RuntimeException{
    public WordListNotFoundException(UUID id) {
        super("WordList with id: " + id + " not found");
    }
}
