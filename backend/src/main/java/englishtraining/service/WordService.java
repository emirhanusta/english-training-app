package englishtraining.service;

import englishtraining.dto.response.WordDto;
import englishtraining.dto.request.WordRequest;
import englishtraining.exception.AlreadyExistException;
import englishtraining.exception.WordNotFoundException;
import englishtraining.model.enums.Level;
import englishtraining.model.Word;
import englishtraining.repository.WordRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class WordService {

    private final WordRepository wordRepository;
    private final ESWordService esWordService;

    public WordService(WordRepository wordRepository,  ESWordService esWordService) {
        this.wordRepository = wordRepository;
        this.esWordService = esWordService;
    }

    public WordDto getWord(UUID id) {
        return WordDto.from(findWordById(id));
    }

    public List<WordDto> getAllWithFilter(int page, int size, String level, String direction, String sortField) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortField);
        PageRequest pageRequest = PageRequest.of(page, size).withSort(sort);
        return wordRepository.findAllByActiveTrue(pageRequest).stream()
                .filter(word -> level.equals("ALL") || Level.valueOf(level).equals(word.getLevel())) // filter by level
                .map(WordDto::from)
                .toList();
    }

    public WordDto createWord(WordRequest wordRequest) {
        checkIfWordAlreadyExists(wordRequest.name());
        Word word = new Word(
                wordRequest.name().toUpperCase(),
                wordRequest.definition(),
                wordRequest.exampleSentences(),
                Level.valueOf(wordRequest.level())
        );
        wordRepository.save(word);
        esWordService.saveESWord(word);
        return WordDto.from(word);
    }

    public WordDto updateWord(UUID id, WordRequest wordRequest) {
        Word word = findWordById(id);
        if (!wordRequest.name().toUpperCase().equals(word.getName())) {
            checkIfWordAlreadyExists(wordRequest.name());
        }
        word.setName(wordRequest.name().toUpperCase());
        word.setDefinition(wordRequest.definition());
        word.setExampleSentences(wordRequest.exampleSentences());
        word.setLevel(Level.valueOf(wordRequest.level()));
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

    private void checkIfWordAlreadyExists(String name) {
        if (wordRepository.existsByNameAndActiveTrue(name.toUpperCase())) {
            throw new AlreadyExistException("Word already exists with this name: " + name);
        }
    }

}
