package englishtraining.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(reason = "Diary not found with id: ", value = HttpStatus.NOT_FOUND)
public class DiaryNotFoundException extends RuntimeException{
    public DiaryNotFoundException(UUID id) {
        super("Diary not found with id: " + id);
    }
}
