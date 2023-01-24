package ke.co.willythedev.acrolinxspellchecker.api;

import ke.co.willythedev.acrolinxspellchecker.other.UniversalResponse;
import ke.co.willythedev.acrolinxspellchecker.service.SpellChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Date;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spell")
public class SpellCheckerController {

    private final SpellChecker spellChecker;

    @PostMapping("/check")
    public Mono<ResponseEntity<UniversalResponse>> checkSpelling(@RequestBody String text) {
        return spellChecker.checkSpelling(text)
                .map(res -> UniversalResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successful spell checking")
                        .data(res)
                        .timestamp(new Date())
                        .build())
                .map(res -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(res));
    }

}
