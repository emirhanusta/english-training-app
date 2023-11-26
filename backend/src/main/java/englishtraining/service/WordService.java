package englishtraining.service;

import englishtraining.dto.response.WordDto;
import englishtraining.dto.request.WordRequest;
import englishtraining.exception.AlreadyExistException;
import englishtraining.exception.InvalidValueException;
import englishtraining.exception.WordNotFoundException;
import englishtraining.model.enums.Level;
import englishtraining.model.Word;
import englishtraining.model.enums.WordStatus;
import englishtraining.model.es.ESWord;
import englishtraining.repository.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class WordService {

    private final Logger logger = LoggerFactory.getLogger(WordService.class);

    private final WordRepository wordRepository;
    private final ESWordService esWordService;

    public WordService(WordRepository wordRepository,  ESWordService esWordService) {
        this.wordRepository = wordRepository;
        this.esWordService = esWordService;
    }

    public WordDto getWord(UUID id) {
        return WordDto.from(findWordById(id));
    }

    public List<WordDto> getAllWithFilter(int page, int size, String level, String status, String direction, String sortField) {
        Sort sort = Sort.by(directionControl(direction),sortField);
        PageRequest pageRequest = PageRequest.of(page, size).withSort(sort);
        return wordRepository.findAllByActiveTrue(pageRequest).stream()
                .filter(word -> level.equals("ALL") || levelControl(level).equals(word.getLevel())) // filter by level
                .filter(word -> status.equals("ALL") || statusControl(status).equals(word.getStatus())) // filter by status
                .map(WordDto::from)
                .toList();
    }

    public Set<WordDto> findSuggestedWordsWithName(String name) {
        Set<ESWord> words = esWordService.findSuggestedWordsWithName(name);
        logger.info("Suggested words listed with name: " + name);
        return words.stream()
                .map(
                        esWord -> new WordDto(
                                esWord.getId(),
                                esWord.getName(),
                                esWord.getDefinition(),
                                esWord.getLevel(),
                                esWord.getStatus(),
                                esWord.getExampleSentences()
                        )
                )
                .collect(Collectors.toSet());
    }

    public WordDto createWord(WordRequest wordRequest) {
        existsWordByName(wordRequest.name());
        Word word = new Word(
                wordRequest.name().toUpperCase(),
                wordRequest.definition(),
                wordRequest.exampleSentences(),
                levelControl(wordRequest.level())
        );
        wordRepository.save(word);
        esWordService.saveESWord(word);
        return WordDto.from(word);
    }

    public WordDto updateWord(UUID id, WordRequest wordRequest) {
        if (!wordRequest.name().equals(findWordById(id).getName())) {
            existsWordByName(wordRequest.name());
        }
        Word word = findWordById(id);
        word.setName(wordRequest.name());
        word.setDefinition(wordRequest.definition());
        word.setExampleSentences(wordRequest.exampleSentences());
        word.setLevel(levelControl(wordRequest.level()));
        word.setStatus(statusControl(wordRequest.status()));
        wordRepository.save(word);
        esWordService.updateESWord(word);
        return WordDto.from(word);
    }

    public void deleteWord(UUID id) {
        Word word = findWordById(id);
        word.setActive(false);
        esWordService.deleteESWord(id);
        wordRepository.save(word);
    }

    protected Word findWordById(UUID id) {
        return wordRepository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new WordNotFoundException("Word not found with this id: " + id)
        );
    }

    private void existsWordByName(String name) {
        if (wordRepository.existsByNameAndActiveTrue(name.toUpperCase())) {
            throw new AlreadyExistException("Word already exists with this name: " + name);
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
