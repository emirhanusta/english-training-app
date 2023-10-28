package englishtraining.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Account not found")
public class WordNotFoundException extends RuntimeException{
    public WordNotFoundException(String id) {
        super("Word with id " + id + " not found");
    }
}

