package englishtraining.dto;

import englishtraining.model.WordList;

import java.util.List;

public record WordListDto(
        String id,
        String name,
        List<WordDto> words
) {
    public static WordListDto from(WordList wordList) {
        if (wordList.getWords() == null) {
            return new WordListDto(
                    wordList.getId(),
                    wordList.getName(),
                    null);
        }
        return new WordListDto(
                wordList.getId(),
                wordList.getName(),
                wordList.getWords().stream().map(WordDto::from).toList());
    }
}
