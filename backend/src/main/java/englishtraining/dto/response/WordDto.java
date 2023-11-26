package englishtraining.dto.response;

import englishtraining.model.Word;

import java.util.List;
import java.util.Objects;

import static java.lang.String.valueOf;

public record WordDto(
        String id,
        String name,
        String definition,
        String level,
        String status,
        List<String> exampleSentences
) {
    public static WordDto from(Word word) {
         return new WordDto(
                 Objects.requireNonNull(word.getId()).toString(),
                 word.getName(),
                 word.getDefinition(),
                 valueOf(word.getLevel()),
                 valueOf(word.getStatus()),
                 word.getExampleSentences() == null ? null : word.getExampleSentences());
    }
}
