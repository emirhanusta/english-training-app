package englishtraining.service;

import englishtraining.dto.request.WordRequest;
import englishtraining.dto.response.WordDto;
import englishtraining.exception.AlreadyExistException;
import englishtraining.exception.WordNotFoundException;
import englishtraining.model.Word;
import englishtraining.model.enums.Level;
import englishtraining.repository.WordRepository;
import org.junit.jupiter.api.*;
import org.springframework.data.domain.*;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WordServiceTest {

    private WordService wordService;
    private  WordRepository wordRepository;
    private ESWordService esWordService;

    @BeforeEach
    void setUp() {
        wordRepository = mock(WordRepository.class);
        esWordService = mock(ESWordService.class);
        wordService = new WordService(wordRepository, esWordService);
    }

    @Test
    @DisplayName("should return wordDto when given wordRequest and word already does not exist")
    void shouldReturnWordDto_WhenGivenWordRequestAndWordAlreadyDoesNotExist() {
        //given
        WordRequest wordRequest = new WordRequest(
                "NAME",
                "definition",
                Level.A1.toString(),
                null
        );
        Word word = new Word(
                wordRequest.name(),
                wordRequest.definition(),
                null,
                Level.valueOf(wordRequest.level())
        );
        //when
        when(wordRepository.save(word)).thenReturn(word);
        WordDto result = wordService.createWord(wordRequest);

        //then
        assertEquals(result.name(), wordRequest.name());
        assertEquals(result.definition(), wordRequest.definition());

        verify(wordRepository, times(1)).save(word);
    }
    @Test
    @DisplayName("Should throw AlreadyExistException when given wordRequest and word already exist")
    void shouldThrowAlreadyExistException_WhenGivenWordRequestAndWordAlreadyExist() {
        //given
        WordRequest wordRequest = new WordRequest(
                "NAME",
                "definition",
                Level.A1.toString(),
                null
        );

        //when
        when(wordRepository.existsByNameAndActiveTrue(wordRequest.name())).thenReturn(true);

        //then
        assertThatThrownBy(() -> wordService.createWord(wordRequest))
                .isInstanceOf(AlreadyExistException.class)
                .hasMessageContaining("Word already exists with this name: " + wordRequest.name());
        verify(wordRepository, times(1)).existsByNameAndActiveTrue(wordRequest.name());
    }

    @Test
    @DisplayName("should return wordDto when given wordRequest and id and word already does not exist")
    void shouldReturnWordDto_WhenGivenWordRequestAndIdAndWordAlreadyDoesNotExist() {
        //given
        UUID id = UUID.randomUUID();
        WordRequest wordRequest = new WordRequest(
                "NAME",
                "definition",
                Level.A1.toString(),
                null
        );
        Word word = new Word(
                wordRequest.name(),
                wordRequest.definition(),
                null,
                Level.valueOf(wordRequest.level())
        );
        //when
        when(wordRepository.findByIdAndActiveTrue(id)).thenReturn(Optional.of(word));
        when(wordRepository.save(word)).thenReturn(word);
        WordDto result = wordService.updateWord(id, wordRequest);

        //then
        assertEquals(result.name(), wordRequest.name());
        assertEquals(result.definition(), wordRequest.definition());

        verify(wordRepository, times(1)).findByIdAndActiveTrue(id);
        verify(wordRepository, times(1)).save(word);
    }

    @Test
    @DisplayName("Should throw AlreadyExistException when given wordRequest and id " +
            "and wordRequest name does not equal word name and word already exist")
    void shouldThrowAlreadyExistException_WhenGivenWordRequestAndIdAndWordRequestNameDoesNotEqualWordNameAndWordAlreadyExist() {
        //given
        UUID id = UUID.randomUUID();
        WordRequest wordRequest = new WordRequest(
                "NAME",
                "definition",
                Level.A1.toString(),
                null
        );
        Word word = new Word(
                "NAME2",
                wordRequest.definition(),
                null,
                Level.valueOf(wordRequest.level())
        );
        //when
        when(wordRepository.findByIdAndActiveTrue(id)).thenReturn(Optional.of(word));
        when(wordRepository.existsByNameAndActiveTrue(wordRequest.name())).thenReturn(true);

        //then
        assertThatThrownBy(() -> wordService.updateWord(id, wordRequest))
                .isInstanceOf(AlreadyExistException.class)

                .hasMessageContaining("Word already exists with this name: " + wordRequest.name());
        verify(wordRepository, times(1)).findByIdAndActiveTrue(id);
        verify(wordRepository, times(1)).existsByNameAndActiveTrue(wordRequest.name());
        verify(wordRepository, times(0)).save(word);
    }


    @Test
    @DisplayName("should return wordDto when given id and word already exist")
    void shouldReturnWordDto_WhenIdExist() {
        //given
        UUID id = UUID.randomUUID();
        Word word = new Word(
                "NAME",
                "definition",
                null,
                Level.A1
        );
        //when
        when(wordRepository.findByIdAndActiveTrue(id)).thenReturn(Optional.of(word));
        WordDto result = wordService.getWord(id);
        //then
        assertEquals(result.name(), word.getName());

        verify(wordRepository, times(1)).findByIdAndActiveTrue(id);
    }

    @Test
    @DisplayName("Should delete word when given id and word exist")
    void shouldDeleteWord_WhenGivenIdAndWordExist() {
        //given
        UUID id = UUID.randomUUID();
        Word word = new Word(
                "NAME",
                "definition",
                null,
                Level.A1
        );
        //when
        when(wordRepository.findByIdAndActiveTrue(id)).thenReturn(Optional.of(word));
        wordService.deleteWord(id);
        //then
        verify(wordRepository, times(1)).findByIdAndActiveTrue(id);
        verify(wordRepository, times(1)).save(word);
    }

    @Test
    @DisplayName("Should return filtered list of WordDto when given page, size, level, status, direction and sortedField")
    void shouldReturnFilteredListOfWordDto_WhenGivenPageAndSizeAndLevelAndStatusAndDirectionAndSortedField() {
        //given
        int page = 0;
        int size = 10;
        String level = Level.A1.toString();
        String direction = "ASC";
        String sortedField = "name";
        Word word = new Word(
                "NAME",
                "definition",
                null,
                Level.A1
        );
        List<Word> words = new ArrayList<>();
        words.add(word);
        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sortedField);
        //when
        when(wordRepository.findAllByActiveTrue(pageable)).thenReturn(words);
        List<WordDto> result = wordService.getAllWithFilter(page, size, level, direction, sortedField);
        //then
        assertEquals(result.size(), words.size());
        verify(wordRepository, times(1)).findAllByActiveTrue(pageable);
    }

    @Test
    @DisplayName("Should throw WordNotFoundException when given id and book does not exist")
    void shouldThrowWordNotFoundException_WhenGivenIdAndWordDoesNotExist() {
        //given
        UUID id = UUID.randomUUID();
        //when
        when(wordRepository.findByIdAndActiveTrue(id)).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> wordService.getWord(id))
                .isInstanceOf(WordNotFoundException.class)
                .hasMessageContaining("Word not found with this id: " + id);
        verify(wordRepository, times(1)).findByIdAndActiveTrue(id);
    }
    @AfterEach
    void tearDown() {
    }}