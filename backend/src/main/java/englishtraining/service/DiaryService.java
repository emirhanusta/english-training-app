package englishtraining.service;

import englishtraining.dto.DiaryDto;
import englishtraining.dto.DiaryRequest;
import englishtraining.exception.DiaryNotFoundException;
import englishtraining.model.Diary;
import englishtraining.repository.DiaryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public DiaryDto getDiary(String id) {
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
                diaryRequest.content()
        );
        return DiaryDto.from(diaryRepository.save(diary));
    }

    public DiaryDto updateDiary(String id, DiaryRequest diaryRequest) {
        Diary diary = findDiaryById(id);
        diary.setTitle(diaryRequest.title());
        diary.setContent(diaryRequest.content());
        return DiaryDto.from(diaryRepository.save(diary));
    }

    public void deleteDiary(String id) {
        diaryRepository.deleteById(id);
    }

    private Diary findDiaryById(String id) {
        return diaryRepository.findById(id).orElseThrow(
                () -> new DiaryNotFoundException(id)
        );
    }
}
