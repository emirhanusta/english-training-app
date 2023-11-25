package englishtraining.service;

import englishtraining.model.Word;
import englishtraining.model.es.ESWord;
import englishtraining.repository.ESWordRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class ESWordService {

    private final Logger logger = Logger.getLogger(ESWordService.class.getName());

    private final ESWordRepository wordModelEsRepository;

    public ESWordService(ESWordRepository wordModelEsRepository) {
        this.wordModelEsRepository = wordModelEsRepository;
    }

    protected void saveESWord(Word word) {
        ESWord esWord = new ESWord(
                Objects.requireNonNull(word.getId()).toString(),
                word.getName(),
                word.getDefinition(),
                word.getExampleSentences(),
                Objects.requireNonNull(word.getLevel()).toString(),
                word.getStatus().toString()
        );
        wordModelEsRepository.save(esWord);
        logger.info("Word saved in ES: " + esWord);
    }

    protected void updateESWord(Word word) {
        wordModelEsRepository.findById(Objects.requireNonNull(word.getId()).toString()).ifPresentOrElse(
                esWord -> {
                    esWord.setName(word.getName());
                    esWord.setDefinition(word.getDefinition());
                    esWord.setExampleSentences(word.getExampleSentences());
                    esWord.setLevel(Objects.requireNonNull(word.getLevel()).toString());
                    esWord.setStatus(word.getStatus().toString());
                    wordModelEsRepository.save(esWord);
                    logger.info("Word updated in ES: " + esWord);
                },
                () -> logger.info("Word not found in ES: " + word.getId().toString()));
    }

    protected void deleteESWord(UUID id) {
        wordModelEsRepository.findById(Objects.requireNonNull(id).toString()).ifPresentOrElse(
                esWord -> {
                    wordModelEsRepository.delete(esWord);
                    logger.info("Word deleted in ES: " + esWord);
                },
                () -> logger.info("Word not found in ES: " + id));
    }
}
