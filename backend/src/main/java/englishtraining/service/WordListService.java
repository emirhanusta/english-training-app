package englishtraining.service;

import englishtraining.dto.WordListDto;
import englishtraining.dto.WordListRequest;
import englishtraining.exception.WordListNotFoundException;
import englishtraining.model.WordList;
import englishtraining.repository.WordListRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class WordListService {

    private final WordListRepository wordListRepository;

    public WordListService(WordListRepository wordListRepository) {
        this.wordListRepository = wordListRepository;
    }

    public WordListDto createWordList(WordListRequest wordListRequest) {
        WordList wordList = new WordList(
                wordListRequest.name()
        );
        return WordListDto.from(wordListRepository.save(wordList));
    }

    public WordListDto getWordList(String id) {
        WordList wordList = findWordListById(id);
        return WordListDto.from(wordList);
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
