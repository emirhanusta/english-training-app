package englishtraining.dto;

import englishtraining.model.Level;
import englishtraining.model.Word;

import java.util.List;
import java.util.Objects;

public record WordDto(
        String id,
        String name,
        String definition,
        Level level,
        List<String> exampleSentences,
        String wordListId
) {
    public static WordDto from(Word word) {
         if (Objects.requireNonNull(word.getExampleSentences()).isEmpty())
            return   new WordDto(
                    word.getId(),
                    word.getName(),
                    word.getDefinition(),
                    word.getLevel(),
                    null,
                    Objects.requireNonNull(word.getWordList()).getId());

         return new WordDto(
                 word.getId(),
                 word.getName(),
                 word.getDefinition(),
                 word.getLevel(),
                 word.getExampleSentences(),
                 Objects.requireNonNull(word.getWordList()).getId());


    }
}
