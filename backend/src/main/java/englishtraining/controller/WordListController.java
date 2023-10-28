package englishtraining.controller;

import englishtraining.dto.WordListDto;
import englishtraining.dto.WordListRequest;
import englishtraining.service.WordListService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/word-list")
public class WordListController {

    private final WordListService wordListService;

    public WordListController(WordListService wordListService) {
        this.wordListService = wordListService;
    }

    @PostMapping("/save")
    public ResponseEntity<WordListDto> createWordList(@Validated @RequestBody WordListRequest wordListRequest) {
        return ResponseEntity.ok(wordListService.createWordList(wordListRequest));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<WordListDto> getWordList(@PathVariable String id) {
        return ResponseEntity.ok(wordListService.getWordList(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WordListDto> updateWordList(String id,@Validated @RequestBody WordListRequest wordListRequest) {
        return ResponseEntity.ok(wordListService.updateWordList(id, wordListRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWordList(@PathVariable String id) {
        wordListService.deleteWordList(id);
        return ResponseEntity.noContent().build();
    }

}
