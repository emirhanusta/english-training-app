package englishtraining.service;

import englishtraining.dto.WordDto;
import englishtraining.dto.WordRequest;
import englishtraining.exception.WordNotFoundException;
import englishtraining.model.Level;
import englishtraining.model.Word;
import englishtraining.model.WordList;
import englishtraining.repository.WordRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService {

    private final WordRepository wordRepository;
    private final WordListService wordListService;

    public WordService(WordRepository wordRepository, WordListService wordListService) {
        this.wordRepository = wordRepository;
        this.wordListService = wordListService;
    }

    public WordDto getWord(String id) {
        return WordDto.from(findWordById(id));
    }

    public List<WordDto> getAllWords(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return wordRepository.findAll(pageRequest).stream()
                .map(WordDto::from)
                .toList();
    }

    public WordDto createWord(WordRequest wordRequest) {
        WordList list = wordListService.findWordListById(wordRequest.wordListId());
        Word word = new Word(
                wordRequest.name(),
                wordRequest.definition(),
                wordRequest.exampleSentences(),
                Level.valueOf(wordRequest.level()),
                list
        );
        return WordDto.from(wordRepository.save(word));
    }

    public WordDto updateWord(String id, WordRequest wordRequest) {
        Word word = findWordById(id);
        word.setName(wordRequest.name());
        word.setDefinition(wordRequest.definition());
        word.setExampleSentences(wordRequest.exampleSentences());
        word.setLevel(Level.valueOf(wordRequest.level()));
        word.setWordList(wordListService.findWordListById(wordRequest.wordListId()));
        return WordDto.from(wordRepository.save(word));
    }

    public void deleteWord(String id) {
        findWordById(id);
        wordRepository.deleteById(id);
    }

    private Word findWordById(String id) {
        return wordRepository.findById(id).orElseThrow(
                () -> new WordNotFoundException(id)
        );
    }

}
