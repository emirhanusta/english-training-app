package englishtraining.controller;

import englishtraining.dto.response.WordListDto;
import englishtraining.dto.request.WordListRequest;
import englishtraining.service.WordListService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/word-list")
public class WordListController {

    private final WordListService wordListService;

    public WordListController(WordListService wordListService) {
        this.wordListService = wordListService;
    }

    @PostMapping("/save")
    public ResponseEntity<WordListDto> createWordList(@Validated @RequestBody WordListRequest wordListRequest) {
        return ResponseEntity.ok(wordListService.createWordList(wordListRequest));
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<WordListDto>> getAllWordLists (@PathVariable UUID userId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(wordListService.getAllWordLists(userId, page, size));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<WordListDto> getWordList(@PathVariable UUID id) {
        return ResponseEntity.ok(wordListService.getWordList(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WordListDto> updateWordList(@PathVariable UUID id,@Validated @RequestBody WordListRequest wordListRequest) {
        return ResponseEntity.ok(wordListService.updateWordList(id, wordListRequest));
    }

    @PutMapping("/addWord/{name}/{wordId}")
    public ResponseEntity<WordListDto> addWord(@PathVariable String name,@PathVariable UUID wordId) {
        return ResponseEntity.ok(wordListService.addWordToWordList(name, wordId));
    }

    @PutMapping("/removeWord/{id}/{wordId}")
    public ResponseEntity<WordListDto> removeWord(@PathVariable UUID id,@PathVariable UUID wordId) {
        return ResponseEntity.ok(wordListService.removeWordFromWordList(id, wordId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWordList(@PathVariable UUID id) {
        wordListService.deleteWordList(id);
        return ResponseEntity.noContent().build();
    }

}
