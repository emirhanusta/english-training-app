package englishtraining.dto;

import englishtraining.model.Level;
import englishtraining.model.Word;
import englishtraining.model.WordStatus;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public record WordDto(
        UUID id,
        String name,
        String definition,
        Level level,
        WordStatus status,
        List<String> exampleSentences,
        UUID wordListId
) {
    public static WordDto from(Word word) {
         if (Objects.requireNonNull(word.getExampleSentences()).isEmpty())
            return   new WordDto(
                    word.getId(),
                    word.getName(),
                    word.getDefinition(),
                    word.getLevel(),
                    word.getStatus(),
                    null,
                    Objects.requireNonNull(word.getWordList()).getId());

         return new WordDto(
                 word.getId(),
                 word.getName(),
                 word.getDefinition(),
                 word.getLevel(),
                 word.getStatus(),
                 word.getExampleSentences(),
                 Objects.requireNonNull(word.getWordList()).getId());


    }
}
