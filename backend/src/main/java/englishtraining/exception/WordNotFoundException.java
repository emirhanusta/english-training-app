package englishtraining.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Account not found")
public class WordNotFoundException extends RuntimeException{
    public WordNotFoundException(UUID id) {
        super("Word with id " + id + " not found");
    }
}

