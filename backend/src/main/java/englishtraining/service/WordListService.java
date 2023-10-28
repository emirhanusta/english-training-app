package englishtraining.service;

import englishtraining.dto.WordListDto;
import englishtraining.dto.WordListRequest;
import englishtraining.exception.WordListNotFoundException;
import englishtraining.model.WordList;
import englishtraining.repository.WordListRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class WordListService {

    private final WordListRepository wordListRepository;

    public WordListService(WordListRepository wordListRepository) {
        this.wordListRepository = wordListRepository;
    }

    public WordListDto getWordList(String id) {
        WordList wordList = findWordListById(id);
        return WordListDto.from(wordList);
    }

    public List<WordListDto> getAllWordLists(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return wordListRepository.findAll(pageRequest).stream()
                .map(WordListDto::from)
                .toList();
    }

    public WordListDto createWordList(WordListRequest wordListRequest) {
        WordList wordList = new WordList(
                wordListRequest.name()
        );
        return WordListDto.from(wordListRepository.save(wordList));
    }

    public WordListDto updateWordList(String id, WordListRequest wordListRequest) {
        WordList wordList = findWordListById(id);
        wordList.setName(wordListRequest.name());
        return WordListDto.from(wordListRepository.save(wordList));
    }

    public void deleteWordList(String id) {
        wordListRepository.deleteById(id);
    }

    protected WordList findWordListById(String id) {
        return wordListRepository.findById(id).orElseThrow(
                () -> new WordListNotFoundException(id)
        );
    }

}
