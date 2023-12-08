package englishtraining.service;

import englishtraining.dto.request.DiaryRequest;
import englishtraining.dto.response.DiaryDto;
import englishtraining.exception.DiaryNotFoundException;
import englishtraining.model.Diary;
import englishtraining.model.User;
import englishtraining.repository.DiaryRepository;
import org.junit.jupiter.api.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiaryServiceTest {

    private DiaryService diaryService;
    private DiaryRepository diaryRepository;
    private UserService userService;


    @BeforeEach
    void setUp() {
        diaryRepository = mock(DiaryRepository.class);
        userService = mock(UserService.class);
        diaryService = new DiaryService(diaryRepository, userService);
    }

    @Test
    @DisplayName("should return diaryDto when given id and diary exists")
    void shouldReturnDiaryDto_WhenGivenId() {
        //given
        UUID id = UUID.randomUUID();
        User user = new User("username", "password", "email");
        Diary diary = new Diary("title", "content", user);
        //when
        when(diaryRepository.findById(id)).thenReturn(Optional.of(diary));
        //then
        DiaryDto result = diaryService.getDiary(id);

        assertEquals(result.id(), diary.getId());
        assertEquals(result.title(), diary.getTitle());

        verify(diaryRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("should throw DiaryNotFoundException when given id and diary does not exist")
    void shouldThrowDiaryNotFoundException_WhenGivenIdAndDiaryDoesNotExist() {
        //given
        UUID id = UUID.randomUUID();
        //when
        when(diaryRepository.findById(id)).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> diaryService.getDiary(id))
                .isInstanceOf(DiaryNotFoundException.class)
                .hasMessageContaining("Diary not found with id: " + id);

        verify(diaryRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("should return list of diaryDto when given page and size")
    void shouldReturnListOfDiaryDto_WhenGivenPageAndSize() {
        //given
        UUID userId = UUID.randomUUID();
        int page = 0;
        int size = 10;
        User user = new User("username", "password", "email");
        user.setId(userId);
        Diary diary = new Diary("title", "content", user);
        Diary diary2 = new Diary("title2", "content2", user);

        List<Diary> diaries = List.of(diary, diary2);
        Page<Diary> diaryPage = new PageImpl<>(diaries);
        //when
        when(diaryRepository.findAllByUserId(userId, PageRequest.of(page, size))).thenReturn( diaryPage);
        //then
        List<DiaryDto> result = diaryService.getAllDiaries(userId, page, size);

        assertEquals(result.size(), 2);

        verify(diaryRepository, times(1)).findAllByUserId(userId, PageRequest.of(page, size));
    }

    @Test
    @DisplayName("should return diaryDto when given diaryRequest")
    void shouldReturnDiaryDto_WhenGivenDiaryRequest() {
        //given
        DiaryRequest diaryRequest = new DiaryRequest("title", "content", UUID.randomUUID());
        User user = new User("username", "password", "email");
        Diary diary = new Diary(diaryRequest.title(), diaryRequest.content(), user);
        //when
        when(userService.findUserById(diaryRequest.userId())).thenReturn(user);
        when(diaryRepository.save(any(Diary.class))).thenAnswer(invocation -> {
            Diary savedDiary = invocation.getArgument(0);
            savedDiary.setId(UUID.randomUUID());
            return savedDiary;
        });
        DiaryDto result = diaryService.createDiary(diaryRequest);

        assertEquals(result.title(), diary.getTitle());

        verify(diaryRepository, times(1)).save(any(Diary.class));
        verify(userService, times(1)).findUserById(diaryRequest.userId());
    }
    @Test
    @DisplayName("should return diaryDto when given diaryRequest and id and diary exists")
    void shouldReturnDiaryDto_WhenGivenDiaryRequestAndIdAndDiaryExists() {
        //given
        UUID id = UUID.randomUUID();
        DiaryRequest diaryRequest = new DiaryRequest("title", "content", UUID.randomUUID());
        User user = new User("username", "password", "email");
        Diary diary = new Diary(diaryRequest.title(), diaryRequest.content(), user);
        //when
        when(diaryRepository.findById(id)).thenReturn(Optional.of(diary));
        when(diaryRepository.save(diary)).thenReturn(diary);
        //then
        DiaryDto result = diaryService.updateDiary(id, diaryRequest);

        assertEquals(result.id(), diary.getId());
        assertEquals(result.title(), diary.getTitle());

        verify(diaryRepository, times(1)).save(diary);
        verify(diaryRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("should throw DiaryNotFoundException when given id and diary does not exist")
    void shouldThrowDiaryNotFoundException_WhenGivenIdAndDiaryDoesNotExist2() {
        //given
        UUID id = UUID.randomUUID();
        DiaryRequest diaryRequest = new DiaryRequest("title", "content", UUID.randomUUID());
        //when
        when(diaryRepository.findById(id)).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> diaryService.updateDiary(id, diaryRequest))
                .isInstanceOf(DiaryNotFoundException.class)
                .hasMessageContaining("Diary not found with id: " + id);

        verify(diaryRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("should delete diary when given id and diary exists")
    void shouldDeleteDiary_WhenGivenIdAndDiaryExists() {
        //given
        UUID id = UUID.randomUUID();
        User user = new User("username", "password", "email");
        Diary diary = new Diary("title", "content", user);
        //when
        when(diaryRepository.findById(id)).thenReturn(Optional.of(diary));
        //then
        diaryService.deleteDiary(id);

        verify(diaryRepository, times(1)).delete(diary);
        verify(diaryRepository, times(1)).findById(id);
    }

    @AfterEach
    void tearDown() {
    }
}