package englishtraining.controller;

import englishtraining.dto.response.DiaryDto;
import englishtraining.dto.request.DiaryRequest;
import englishtraining.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/diary")
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<DiaryDto>> getAllDiaries (@PathVariable UUID userId,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(diaryService.getAllDiaries(userId, page, size));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DiaryDto> getDiary(@PathVariable UUID id) {
        return ResponseEntity.ok(diaryService.getDiary(id));
    }

    @PostMapping("/save")
    public ResponseEntity<DiaryDto> createDiary(@Validated @RequestBody DiaryRequest diaryRequest) {
        return ResponseEntity.ok(diaryService.createDiary(diaryRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DiaryDto> updateDiary(@PathVariable UUID id,@Validated @RequestBody DiaryRequest diaryRequest) {
        return ResponseEntity.ok(diaryService.updateDiary(id, diaryRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDiary(@PathVariable UUID id) {
        diaryService.deleteDiary(id);
        return ResponseEntity.noContent().build();
    }
}

