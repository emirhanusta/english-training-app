package englishtraining.exception;

public class WordListNotFoundException extends RuntimeException{
    public WordListNotFoundException(String id) {
        super("Word list with id " + id + " not found");
    }
}
