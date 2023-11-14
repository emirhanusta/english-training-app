package englishtraining.dto;

import englishtraining.model.Level;
import englishtraining.model.Word;
import englishtraining.model.WordStatus;

import java.util.List;
import java.util.UUID;

public record WordDto(
        UUID id,
        String name,
        String definition,
        Level level,
        WordStatus status,
        List<String> exampleSentences
) {
    public static WordDto from(Word word) {
         return new WordDto(
                 word.getId(),
                 word.getName(),
                 word.getDefinition(),
                 word.getLevel(),
                 word.getStatus(),
                 word.getExampleSentences() == null ? null : word.getExampleSentences());
    }
}
