package englishtraining.controller;

import englishtraining.dto.WordDto;
import englishtraining.dto.WordRequest;
import englishtraining.service.WordService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/word")
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @PostMapping("/save")
    public ResponseEntity<WordDto> createWord(@Validated @RequestBody WordRequest wordRequest) {
        return ResponseEntity.ok(wordService.createWord(wordRequest));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<WordDto> getWord(@PathVariable String id) {
        return ResponseEntity.ok(wordService.getWord(id));
    }

    @GetMapping("/getAllByLevel")
    public ResponseEntity<List<WordDto>> getAllWordsByLevel (@RequestParam String level,
                                                             @RequestParam(defaultValue = "desc") String direction,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(wordService.getAllWordsByLevel(page, size, level, direction));
    }

    @GetMapping("/getAllByStatus")
    public ResponseEntity<List<WordDto>> getAllWordsByStatus (@RequestParam String status,
                                                              @RequestParam(defaultValue = "desc") String direction,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(wordService.getAllWordsByStatus(page, size, status, direction));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<WordDto> updateWord(@PathVariable String id,@Validated @RequestBody WordRequest wordRequest) {
        return ResponseEntity.ok(wordService.updateWord(id, wordRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWord(@PathVariable String id) {
        wordService.deleteWord(id);
        return ResponseEntity.noContent().build();
    }

}
