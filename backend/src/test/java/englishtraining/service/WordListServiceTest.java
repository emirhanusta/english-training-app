package englishtraining.service;

import englishtraining.dto.request.WordListRequest;
import englishtraining.dto.response.WordListDto;
import englishtraining.exception.AlreadyExistException;
import englishtraining.exception.WordListNotFoundException;
import englishtraining.model.User;
import englishtraining.model.Word;
import englishtraining.model.WordList;
import englishtraining.model.enums.Level;
import englishtraining.model.enums.WordStatus;
import englishtraining.repository.WordListRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WordListServiceTest {

    private WordListService wordListService;
    private WordListRepository wordListRepository;
    private UserService userService;
    private WordService wordService;

    @BeforeEach
    void setUp() {
        wordListRepository = mock(WordListRepository.class);
        userService = mock(UserService.class);
        wordService = mock(WordService.class);
        wordListService = new WordListService(wordListRepository, wordService, userService);
    }

    @Test
    @DisplayName("should return wordListDto when given id and wordList exist")
    void shouldReturnWordListDto_WhenGivenIdAndWordListExist() {
        //given
        UUID id = UUID.randomUUID();
        User user = new User("username", "password", "email");
        WordList wordList = new WordList("name", null, user);
        //when
        when(wordListRepository.findById(id)).thenReturn(Optional.of(wordList));

        //then
        WordListDto result = wordListService.getWordList(id);

        assertEquals(result.name(), wordList.getName());

        verify(wordListRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("should throw WordListNotFoundException when given id and wordList does not exist")
    void shouldThrowWordListNotFoundException_WhenGivenIdAndWordListDoesNotExist() {
        //given
        UUID id = UUID.randomUUID();
        //when
        when(wordListRepository.findById(id)).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> wordListService.getWordList(id))
                .isInstanceOf(WordListNotFoundException.class)
                .hasMessageContaining("Word list not found with id: " + id);

        verify(wordListRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("should return list of wordListDto when given page and size")
    void shouldReturnListOfWordListDto_WhenGivenPageAndSize() {
        //given
        int page = 0;
        int size = 10;
        User user = new User("username", "password", "email");
        WordList wordList = new WordList("name", null, user);
        WordList wordList2 = new WordList("name2", null, user);

        List<WordList> wordLists = List.of(wordList, wordList2);
        Page<WordList> wordListPage = new PageImpl<>(wordLists);
        //when
        when(wordListRepository.findAll(PageRequest.of(page, size))).thenReturn(wordListPage);
        //then
        List<WordListDto> result = wordListService.getAllWordLists(page, size);

        assertEquals(result.size(), wordLists.size());

        verify(wordListRepository, times(1)).findAll(PageRequest.of(page, size));
    }

    @Test
    @DisplayName("should return wordListDto when given wordListRequest and wordList with this name does not exist")
    void shouldReturnWordListDto_WhenGivenWordListRequest() {
        //given
        WordListRequest wordListRequest = new WordListRequest("name", UUID.randomUUID());
        User user = new User("username", "password", "email");
        WordList wordList = new WordList("name", null, user);
        //when
        when(userService.findUserById(wordListRequest.userId())).thenReturn(user);
        when(wordListRepository.existsByName(wordList.getName())).thenReturn(false);
        when(wordListRepository.save(wordList)).thenReturn(wordList);
        //then
        WordListDto result = wordListService.createWordList(wordListRequest);
        assertEquals(result.name(), wordListRequest.name());

        verify(wordListRepository, times(1)).save(wordList);
        verify(userService, times(1)).findUserById(wordListRequest.userId());
        verify(wordListRepository, times(1)).existsByName(wordList.getName());
    }

    @Test
    @DisplayName("should throw AlreadyExistException when given wordListRequest and wordList with this name exist")
    void shouldThrowAlreadyExistException_WhenGivenWordListRequest() {
        //given
        WordListRequest wordListRequest = new WordListRequest("name", UUID.randomUUID());
        User user = new User("username", "password", "email");
        WordList wordList = new WordList("name", null, user);
        //when
        when(wordListRepository.existsByName(wordList.getName())).thenReturn(true);
        //then
        assertThatThrownBy(() -> wordListService.createWordList(wordListRequest))
                .isInstanceOf(AlreadyExistException.class)
                .hasMessageContaining("Word list already exists with name: " + wordList.getName());

        verify(wordListRepository, times(0)).save(wordList);
        verify(userService, times(0)).findUserById(wordListRequest.userId());
        verify(wordListRepository, times(1)).existsByName(wordList.getName());
    }

    @Test
    @DisplayName("should return wordListDto when given wordListRequest and id " +
            "and WordListRequest name is not equal to wordList name and wordList with this name does not exist")
    void shouldReturnWordListDto_WhenGivenWordListRequestAndId() {
        //given
        UUID id = UUID.randomUUID();
        WordListRequest wordListRequest = new WordListRequest("name", UUID.randomUUID());
        User user = new User("username", "password", "email");
        WordList wordList = new WordList("name2", null, user);
        //when
        when(wordListRepository.findById(id)).thenReturn(Optional.of(wordList));
        when(wordListRepository.existsByName(wordList.getName())).thenReturn(false);
        when(wordListRepository.save(wordList)).thenReturn(wordList);
        //then
        WordListDto result = wordListService.updateWordList(id, wordListRequest);
        assertEquals(result.name(), wordListRequest.name());

        verify(wordListRepository, times(1)).save(wordList);
        verify(wordListRepository, times(1)).existsByName(wordList.getName());
        verify(wordListRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("should throw AlreadyExistException when given wordListRequest and id " +
            "and WordListRequest name is not equal to wordList name and wordList with this name exist")
    void shouldThrowAlreadyExistException_WhenGivenWordListRequestAndId() {
        //given
        UUID id = UUID.randomUUID();
        WordListRequest wordListRequest = new WordListRequest("name", UUID.randomUUID());
        User user = new User("username", "password", "email");
        WordList wordList = new WordList("name2", null, user);

        //when
        when(wordListRepository.findById(id)).thenReturn(Optional.of(wordList));
        when(wordListRepository.existsByName(wordListRequest.name())).thenReturn(true);

        //then
        assertThatThrownBy(() -> wordListService.updateWordList(id, wordListRequest))
                .isInstanceOf(AlreadyExistException.class)
                .hasMessageContaining("Word list already exists with name: " + wordListRequest.name());

        verify(wordListRepository, times(1)).existsByName(wordListRequest.name());
        verify(wordListRepository, times(1)).findById(id);
        verify(wordListRepository, times(0)).save(wordList);
    }

    @Test
    @DisplayName("should delete wordList when given id and wordList exist")
    void shouldDeleteWordList_WhenGivenIdAndWordListExist() {
        //given
        UUID id = UUID.randomUUID();
        User user = new User("username", "password", "email");
        WordList wordList = new WordList("name", null, user);
        //when
        when(wordListRepository.findById(id)).thenReturn(Optional.of(wordList));
        //then
        wordListService.deleteWordList(id);

        verify(wordListRepository, times(1)).delete(wordList);
        verify(wordListRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("should throw NotFoundException when given id and wordList does not exist")
    void shouldThrowNotFoundException_WhenGivenIdAndWordListDoesNotExist() {
        //given
        UUID id = UUID.randomUUID();
        //when
        when(wordListRepository.findById(id)).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> wordListService.deleteWordList(id))
                .isInstanceOf(WordListNotFoundException.class)
                .hasMessageContaining("Word list not found with id: " + id);

        verify(wordListRepository, times(0)).delete(any());
        verify(wordListRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("should throw when AlreadyExistException given name and wordId and wordList exist with name" +
            "and wordList contain word with this id")
    void shouldThrowAlreadyExistException_WhenGivenNameAndWordIdAndWordListExistWithNameAndWordListContainWordWithThisId() {
        // given
        String wordListName = "MyWordList";
        UUID wordId = UUID.randomUUID();
        User user = new User("username", "password", "email");
        WordList wordList = new WordList(wordListName, new ArrayList<>(), user);
        Word existingWord = new Word("TestWord", "TestDefinition", null, Level.A1, WordStatus.LEARNING);
        existingWord.setId(wordId);
        Objects.requireNonNull(wordList.getWords()).add(existingWord);

        //when
        when(wordListRepository.findByName(wordListName)).thenReturn(Optional.of(wordList));

        // when and then
        assertThatThrownBy(() -> wordListService.addWordToWordList(wordListName, wordId))
                .isInstanceOf(AlreadyExistException.class)
                .hasMessageContaining("Word already exist in word list with id: " + wordId);

        // verify
        verify(wordListRepository, times(1)).findByName(wordListName);
        verify(wordService, times(0)).findWordById(wordId);
        verify(wordListRepository, times(0)).save(wordList);
    }

    @Test
    @DisplayName("should return wordListDto when given name and wordId and wordList exist with name" +
            "and wordList does not contain word with this id")
    void shouldReturnWordListDto_WhenGivenNameAndWordIdAndWordListExistWithNameAndWordListDoesNotContainWordWithThisId() {
        String wordListName = "MyWordList";
        UUID wordId = UUID.randomUUID();
        User user = new User("username", "password", "email");
        WordList wordList = new WordList(wordListName, new ArrayList<>(), user);
        Word word = new Word("TestWord", "TestDefinition", null, Level.A1, WordStatus.LEARNING);
        word.setId(wordId);

        // when
        when(wordListRepository.findByName(wordListName)).thenReturn(Optional.of(wordList));
        when(wordService.findWordById(wordId)).thenReturn(word);
        when(wordListRepository.save(wordList)).thenReturn(wordList);

        // then
        WordListDto result = wordListService.addWordToWordList(wordListName, wordId);

        assertEquals(wordListName, result.name());
        assertEquals(1, result.words().size());
        assertEquals(wordId, result.words().get(0).id());

        // verify
        verify(wordListRepository, times(1)).findByName(wordListName);
        verify(wordService, times(1)).findWordById(wordId);
        verify(wordListRepository, times(1)).save(wordList);
    }

    @Test
    @DisplayName("should throw NotFoundException when given name and wordId and wordList does not exist with name")
    void shouldThrowNotFoundException_WhenGivenNameAndWordIdAndWordListDoesNotExistWithName() {
        // given
        String wordListName = "MyWordList";
        UUID wordId = UUID.randomUUID();

        // when
        when(wordListRepository.findByName(wordListName)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> wordListService.addWordToWordList(wordListName, wordId))
                .isInstanceOf(WordListNotFoundException.class)
                .hasMessageContaining("Word list not found with name: " + wordListName);

        // verify
        verify(wordListRepository, times(1)).findByName(wordListName);
        verify(wordService, times(0)).findWordById(wordId);
        verify(wordListRepository, times(0)).save(any());
    }
    @AfterEach
    void tearDown() {
    }
}