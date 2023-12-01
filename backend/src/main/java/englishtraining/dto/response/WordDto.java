package englishtraining.dto.response;

import englishtraining.model.Word;

import java.util.List;
import java.util.UUID;

import static java.lang.String.valueOf;

public record WordDto(
        UUID id,
        String name,
        String definition,
        String level,
        String status,
        List<String> exampleSentences
) {
    public static WordDto from(Word word) {
         return new WordDto(
                 word.getId(),
                 word.getName(),
                 word.getDefinition(),
                 valueOf(word.getLevel()),
                 valueOf(word.getStatus()),
                 word.getExampleSentences() == null ? null : word.getExampleSentences());
    }
}
