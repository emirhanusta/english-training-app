package englishtraining.controller;

import englishtraining.dto.WordDto;
import englishtraining.dto.WordRequest;
import englishtraining.service.WordService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/word")
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
    public ResponseEntity<WordDto> getWord(@PathVariable UUID id) {
        return ResponseEntity.ok(wordService.getWord(id));
    }


    @GetMapping("/getAllWithFilter")
    public ResponseEntity<List<WordDto>> getAllWithFilter (@RequestParam(defaultValue = "desc") String direction,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size,
                                                              @RequestParam(defaultValue = "ALL") String level,
                                                              @RequestParam(defaultValue = "ALL") String status,
                                                              @RequestParam(defaultValue = "name") String sortField) {
        return ResponseEntity.ok(wordService.getAllWithFilter(page, size, level, status, direction, sortField));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<WordDto> updateWord(@PathVariable UUID id,@Validated @RequestBody WordRequest wordRequest) {
        return ResponseEntity.ok(wordService.updateWord(id, wordRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWord(@PathVariable UUID id) {
        wordService.deleteWord(id);
        return ResponseEntity.noContent().build();
    }

}
