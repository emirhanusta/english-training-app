package englishtraining.service;

import englishtraining.dto.WordListDto;
import englishtraining.dto.WordListRequest;
import englishtraining.exception.AlreadyExistException;
import englishtraining.exception.WordListNotFoundException;
import englishtraining.model.WordList;
import englishtraining.repository.WordListRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
public class WordListService {

    private final WordListRepository wordListRepository;
    private final WordService wordService;

    public WordListService(WordListRepository wordListRepository, WordService wordService) {
        this.wordListRepository = wordListRepository;
        this.wordService = wordService;
    }

    public WordListDto getWordList(UUID id) {
        WordList wordList = findWordListById(id);
        return WordListDto.from(wordList);
    }

    public List<WordListDto> getAllWordLists(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return wordListRepository.findAllByActiveTrue(pageRequest)
                .stream()
                .map(WordListDto::from)
                .toList();
    }

    public WordListDto createWordList(WordListRequest wordListRequest) {
        WordList wordList = new WordList(
                wordListRequest.name()
        );
        return WordListDto.from(wordListRepository.save(wordList));
    }

    public WordListDto updateWordList(UUID id, WordListRequest wordListRequest) {
        WordList wordList = findWordListById(id);
        wordList.setName(wordListRequest.name());
        return WordListDto.from(wordListRepository.save(wordList));
    }

    public void deleteWordList(UUID id) {
        WordList wordList = findWordListById(id);
        Objects.requireNonNull(wordList.getWords()).forEach(word -> word.setActive(false));
        wordList.setActive(false);
        wordListRepository.save(wordList);
    }

    public WordListDto addWordToWordList(String name, UUID wordId) {
        WordList wordList = findWordListByName(name);
        Objects.requireNonNull(wordList.getWords())
                .stream()
                .filter(word -> Objects.equals(word.getId(), wordId))
                .findAny()
                .ifPresentOrElse(
                        word -> {
                            throw new AlreadyExistException("Word already exist in word list with id: " + wordId);
                        },
                        () -> {
                            wordList.getWords().add(wordService.findWordById(wordId));
                            wordListRepository.save(wordList);
                        }
                );
        return WordListDto.from(wordList);
    }

    private WordList findWordListByName(String name) {
        return wordListRepository.findByNameAndActiveTrue(name).orElseThrow(
                () -> new WordListNotFoundException("Word list not found with name: " + name)
        );
    }

    private WordList findWordListById(UUID id) {
        return wordListRepository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new WordListNotFoundException("Word list not found with id: " + id)
        );
    }
}
