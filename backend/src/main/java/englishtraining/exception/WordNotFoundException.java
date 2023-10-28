package englishtraining.exception;

public class WordNotFoundException extends RuntimeException{
    public WordNotFoundException(String id) {
        super("Word with id " + id + " not found");
    }
}

