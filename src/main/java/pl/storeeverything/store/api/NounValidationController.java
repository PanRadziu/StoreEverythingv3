package pl.storeeverything.store.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/noun-checker")
public class NounValidationController {

    private static final List<String> NOUNS = Arrays.asList("Sport", "Technology", "Music", "Science", "Movies", "News", "Shops");

    @GetMapping("/{word}")
    public ResponseEntity<?> checkIfNoun(@PathVariable String word) {
        if (isNoun(word)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The provided word is not recognized as a noun.");
        }
    }

    private boolean isNoun(String word) {
        return NOUNS.contains(word) || word.endsWith("ion") || word.endsWith("ity");
    }
}
