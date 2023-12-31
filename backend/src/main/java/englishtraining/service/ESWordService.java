package englishtraining.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import englishtraining.dto.response.WordDto;
import englishtraining.exception.WordNotFoundException;
import englishtraining.model.Word;
import englishtraining.model.es.ESWord;
import englishtraining.repository.ESWordRepository;
import englishtraining.utils.ESUtil;
import co.elastic.clients.elasticsearch.core.search.Hit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ESWordService {

    private final Logger logger = LoggerFactory.getLogger(ESWordService.class);

    private final ESWordRepository esWordRepository;
    private final ElasticsearchClient elasticsearchClient;

    public ESWordService(ESWordRepository wordModelEsRepository, ElasticsearchClient elasticsearchClient) {
        this.esWordRepository = wordModelEsRepository;
        this.elasticsearchClient = elasticsearchClient;
    }

    public List<WordDto> findSuggestedWordsWithName(String name) {
        Query autoSuggestQuery = ESUtil.buildAutoSuggestQuery(name);
        logger.info("Elasticsearch query: "+ autoSuggestQuery.toString());
        try {
            return elasticsearchClient.search(q -> q.index("words_index").query(autoSuggestQuery), ESWord.class)
                    .hits()
                    .hits()
                    .stream()
                    .map(Hit::source).filter(Objects::nonNull)
                    .map(
                            esWord -> new WordDto(
                                    UUID.fromString(Objects.requireNonNull(esWord.getId())),
                                    esWord.getName(),
                                    esWord.getDefinition(),
                                    esWord.getLevel(),
                                    esWord.getExampleSentences()
                            ))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    protected void saveESWord(Word word) {
        ESWord esWord = new ESWord(
                Objects.requireNonNull(word.getId()).toString(),
                word.getName(),
                word.getDefinition(),
                word.getExampleSentences(),
                Objects.requireNonNull(word.getLevel()).toString()
        );
        esWordRepository.save(esWord);
        logger.info("Word saved in ES: " + esWord);
    }

    protected void updateESWord(Word word) {
        esWordRepository.findById(Objects.requireNonNull(word.getId()).toString()).ifPresentOrElse(
                esWord -> {
                    esWord.setName(word.getName());
                    esWord.setDefinition(word.getDefinition());
                    esWord.setExampleSentences(word.getExampleSentences());
                    esWord.setLevel(Objects.requireNonNull(word.getLevel()).toString());
                    esWordRepository.save(esWord);
                    logger.info("Word updated in ES: " + esWord);
                },
                () -> {
                    logger.error("Word not found in ES: " + word.getId());
                    throw new WordNotFoundException("Word not found in ES: " + word.getId());
                });
    }

    protected void deleteESWord(UUID id) {
        esWordRepository.findById(Objects.requireNonNull(id).toString()).ifPresentOrElse(
                esWord -> {
                    esWordRepository.delete(esWord);
                    logger.info("Word deleted in ES: " + esWord);
                },
                () ->  {
                    logger.error("Word not found in ES: " + id);
                    throw new WordNotFoundException("Word not found in ES: " + id);
                });
    }
}
