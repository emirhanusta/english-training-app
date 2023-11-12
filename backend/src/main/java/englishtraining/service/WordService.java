package englishtraining.service;

import englishtraining.dto.WordDto;
import englishtraining.dto.WordRequest;
import englishtraining.exception.InvalidValueException;
import englishtraining.exception.WordNotFoundException;
import englishtraining.model.Level;
import englishtraining.model.Word;
import englishtraining.model.WordList;
import englishtraining.model.WordStatus;
import englishtraining.repository.WordRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WordService {

    private final WordRepository wordRepository;
    private final WordListService wordListService;

    public WordService(WordRepository wordRepository, WordListService wordListService) {
        this.wordRepository = wordRepository;
        this.wordListService = wordListService;
    }

    public WordDto getWord(UUID id) {
        return WordDto.from(findWordById(id));
    }

    public List<WordDto> getAllWordsByStatus(int page, int size, String status, String direction) {
        Sort sort = Sort.by(directionControl(direction),"createdAt");
        PageRequest pageRequest = PageRequest.of(page, size).withSort(sort);
        return wordRepository.findAllByActiveTrue(pageRequest).stream()
                .filter(word -> statusControl(status).equals(word.getStatus()))
                .map(WordDto::from)
                .toList();
    }

    public List<WordDto> getAllWordsByLevel(int page, int size, String level, String direction) {
        Sort sort = Sort.by(directionControl(direction),"createdAt");
        PageRequest pageRequest = PageRequest.of(page, size).withSort(sort);
        return wordRepository.findAllByActiveTrue(pageRequest).stream()
                .filter(word -> levelControl(level).equals(word.getLevel()))
                .map(WordDto::from)
                .toList();
    }

    public WordDto createWord(WordRequest wordRequest) {
        WordList list = wordListService.findWordListById(wordRequest.wordListId());
        existsWordByName(wordRequest.name());
        Word word = new Word(
                wordRequest.name().toUpperCase(),
                wordRequest.definition(),
                wordRequest.exampleSentences(),
                levelControl(wordRequest.level()),
                list
        );
        return WordDto.from(wordRepository.save(word));
    }

    public WordDto updateWord(UUID id, WordRequest wordRequest) {
        if (!wordRequest.name().equals(findWordById(id).getName())) {
            existsWordByName(wordRequest.name());
        }
        Word word = findWordById(id);
        word.setName(wordRequest.name());
        word.setDefinition(wordRequest.definition());
        word.setExampleSentences(wordRequest.exampleSentences());
        word.setLevel(Level.valueOf(wordRequest.level()));
        word.setWordList(wordListService.findWordListById(wordRequest.wordListId()));
        return WordDto.from(wordRepository.save(word));
    }

    public void deleteWord(UUID id) {
        wordRepository.deleteById(id);
    }

    private Word findWordById(UUID id) {
        return wordRepository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new WordNotFoundException(id)
        );
    }

    private void existsWordByName(String name) {
        if (wordRepository.existsByNameAndActiveTrue(name.toUpperCase())) {
            throw new InvalidValueException("Word already exists with this name: " + name);
        }
    }

    private Sort.Direction directionControl(String direction) {
        return switch (direction.toLowerCase()) {
            case "asc" -> Sort.Direction.ASC;
            case "desc" -> Sort.Direction.DESC;
            default -> throw new InvalidValueException("Invalid direction");
        };
    }

    private Level levelControl(String level) {
        return switch (level.toLowerCase()) {
            case "a1" -> Level.A1;
            case "a2" -> Level.A2;
            case "b1" -> Level.B1;
            case "b2" -> Level.B2;
            case "c1" -> Level.C1;
            case "c2" -> Level.C2;
            default -> throw new InvalidValueException("Invalid level");
        };
    }

    private WordStatus statusControl(String status) {
        return switch (status.toLowerCase()) {
            case "learning" -> WordStatus.LEARNING;
            case "learned" -> WordStatus.LEARNED;
            default -> throw new InvalidValueException("Invalid status");
        };
    }
}
