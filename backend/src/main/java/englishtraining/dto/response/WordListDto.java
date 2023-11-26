package englishtraining.dto.response;

import englishtraining.model.WordList;

import java.util.List;
import java.util.UUID;

public record WordListDto(
        UUID id,
        String name,
        List<WordDto> words
) {
    public static WordListDto from(WordList wordList) {
        return new WordListDto(
                wordList.getId(),
                wordList.getName(),
                wordList.getWords() == null ? null : wordList.getWords()
                        .stream()
                        .map(WordDto::from)
                        .toList());
    }
}
