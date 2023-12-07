package englishtraining.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import englishtraining.exception.WordNotFoundException;
import englishtraining.model.Word;
import englishtraining.model.enums.Level;
import englishtraining.model.enums.WordStatus;
import englishtraining.model.es.ESWord;
import englishtraining.repository.ESWordRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ESWordServiceTest {

    private ESWordService esWordService;
    private ESWordRepository esWordRepository;
    private ElasticsearchClient elasticsearchClient;

    @BeforeEach
    void setUp() {
        esWordRepository = mock(ESWordRepository.class);
        elasticsearchClient = mock(ElasticsearchClient.class);
        esWordService = new ESWordService(esWordRepository, elasticsearchClient);
    }

    @Test
    @DisplayName("should save ESWord when given word")
    void shouldSaveESWord_WhenGivenWord() {
        // given
        UUID id = UUID.randomUUID();
        Word word = new Word(
                "NAME",
                "definition",
                null,
                Level.A1,
                WordStatus.LEARNING
        );
        word.setId(id);
        ESWord esWord = new ESWord(
                Objects.requireNonNull(word.getId()).toString(),
                word.getName(),
                word.getDefinition(),
                null,
                Objects.requireNonNull(word.getLevel()).toString(),
                word.getStatus().toString());

        // when
        when(esWordRepository.save(esWord)).thenReturn(esWord);

        // then
        esWordService.saveESWord(word);

        verify(esWordRepository, times(1)).save(esWord);
    }

    @Test
    @DisplayName("should update word in elasticsearch when updateESWord is called with a word")
    void shouldUpdateWordInElasticsearchWhenUpdateESWordIsCalledWithAWord() {
        // given
        UUID id = UUID.randomUUID();
        Word word = new Word(
                "NAME",
                "definition",
                null,
                Level.A1,
                WordStatus.LEARNING
        );
        word.setId(id);
        ESWord esWord = new ESWord(
                Objects.requireNonNull(word.getId()).toString(),
                word.getName(),
                word.getDefinition(),
                null,
                Objects.requireNonNull(word.getLevel()).toString(),
                word.getStatus().toString());

        // when
        when(esWordRepository.findById(anyString())).thenReturn(Optional.of(esWord));
        when(esWordRepository.save(esWord)).thenReturn(esWord);

        // then
        esWordService.updateESWord(word);
        assertEquals(esWord.getName(), word.getName());

        verify(esWordRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("should throw WordNotFoundException when updateESWord is called with a word that does not exist")
    void shouldThrowWordNotFoundExceptionWhenUpdateESWordIsCalledWithAWordThatDoesNotExist() {
        // given
        UUID id = UUID.randomUUID();
        Word word = new Word(
                "NAME",
                "definition",
                null,
                Level.A1,
                WordStatus.LEARNING
        );
        word.setId(id);

        // when
        when(esWordRepository.findById(anyString())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> esWordService.updateESWord(word))
                .isInstanceOf(WordNotFoundException.class)
                .hasMessageContaining("Word not found in ES: " + word.getId());

        verify(esWordRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("should delete word in elasticsearch when deleteESWord is called with a id")
    void shouldDeleteWordInElasticsearchWhenDeleteESWordIsCalledWithAId() {
        // given
        UUID id = UUID.randomUUID();
        Word word = new Word(
                "NAME",
                "definition",
                null,
                Level.A1,
                WordStatus.LEARNING
        );
        word.setId(id);
        ESWord esWord = new ESWord(
                Objects.requireNonNull(word.getId()).toString(),
                word.getName(),
                word.getDefinition(),
                null,
                Objects.requireNonNull(word.getLevel()).toString(),
                word.getStatus().toString());

        // when
        when(esWordRepository.findById(anyString())).thenReturn(Optional.of(esWord));

        // then
        esWordService.deleteESWord(word.getId());

        verify(esWordRepository, times(1)).findById(anyString());
        verify(esWordRepository, times(1)).delete(esWord);
    }

    @Test
    @DisplayName("should throw WordNotFoundException when deleteESWord is called with a id that does not exist")
    void shouldThrowWordNotFoundExceptionWhenDeleteESWordIsCalledWithAIdThatDoesNotExist() {
        // given
        UUID id = UUID.randomUUID();

        // when
        when(esWordRepository.findById(anyString())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> esWordService.deleteESWord(id))
                .isInstanceOf(WordNotFoundException.class)
                .hasMessageContaining("Word not found in ES: " + id);

        verify(esWordRepository, times(1)).findById(anyString());
    }
    @AfterEach
    void tearDown() {
    }
}