package englishtraining.service;

import englishtraining.dto.response.WordListDto;
import englishtraining.dto.request.WordListRequest;
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
    private final UserService userService;
    public WordListService(WordListRepository wordListRepository, WordService wordService, UserService userService) {
        this.wordListRepository = wordListRepository;
        this.wordService = wordService;
        this.userService = userService;
    }

    public WordListDto getWordList(UUID id) {
        WordList wordList = findWordListById(id);
        return WordListDto.from(wordList);
    }

    public List<WordListDto> getAllWordLists(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return wordListRepository.findAll(pageRequest)
                .stream()
                .map(WordListDto::from)
                .toList();
    }

    public WordListDto createWordList(WordListRequest wordListRequest) {
        checkIfWordListAlreadyExists(wordListRequest.name());
        WordList wordList = new WordList(
                wordListRequest.name(),
                userService.findUserById(wordListRequest.userId())
        );
        return WordListDto.from(wordListRepository.save(wordList));
    }

    public WordListDto updateWordList(UUID id, WordListRequest wordListRequest) {
        WordList wordList = findWordListById(id);
        if (!Objects.equals(wordList.getName(), wordListRequest.name())) {
            checkIfWordListAlreadyExists(wordListRequest.name());
        }
        wordList.setName(wordListRequest.name());
        return WordListDto.from(wordListRepository.save(wordList));
    }

    public void deleteWordList(UUID id) {
        wordListRepository.delete(findWordListById(id));
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
        return wordListRepository.findByName(name).orElseThrow(
                () -> new WordListNotFoundException("Word list not found with name: " + name)
        );
    }

    private WordList findWordListById(UUID id) {
        return wordListRepository.findById(id).orElseThrow(
                () -> new WordListNotFoundException("Word list not found with id: " + id)
        );
    }

    private void checkIfWordListAlreadyExists(String name) {
        if (wordListRepository.existsByName(name)) {
            throw new AlreadyExistException("Word list already exists with name: " + name);
        }
    }
}
