package englishtraining.service;

import englishtraining.dto.response.DiaryDto;
import englishtraining.dto.request.DiaryRequest;
import englishtraining.exception.DiaryNotFoundException;
import englishtraining.model.Diary;
import englishtraining.repository.DiaryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final UserService userService;

    public DiaryService(DiaryRepository diaryRepository, UserService userService) {
        this.diaryRepository = diaryRepository;
        this.userService = userService;
    }

    public DiaryDto getDiary(UUID id) {
        return DiaryDto.from(findDiaryById(id));
    }

    public List<DiaryDto> getAllDiaries(int page, int size) {
        return diaryRepository.findAll(PageRequest.of(page, size)).stream()
                .map(DiaryDto::from)
                .toList();
    }

    public DiaryDto createDiary(DiaryRequest diaryRequest) {
        Diary diary = new Diary(
                diaryRequest.title(),
                diaryRequest.content(),
                userService.findUserById(UUID.fromString(diaryRequest.userId()))
        );
        return DiaryDto.from(diaryRepository.save(diary));
    }

    public DiaryDto updateDiary(UUID id, DiaryRequest diaryRequest) {
        Diary diary = findDiaryById(id);
        diary.setTitle(diaryRequest.title());
        diary.setContent(diaryRequest.content());
        return DiaryDto.from(diaryRepository.save(diary));
    }

    public void deleteDiary(UUID id) {
        diaryRepository.deleteById(id);
    }

    private Diary findDiaryById(UUID id) {
        return diaryRepository.findById(id).orElseThrow(
                () -> new DiaryNotFoundException(id)
        );
    }
}
