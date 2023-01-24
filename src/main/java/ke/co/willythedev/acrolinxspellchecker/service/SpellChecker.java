package ke.co.willythedev.acrolinxspellchecker.service;

import ke.co.willythedev.acrolinxspellchecker.model.SpellCheckResponse;
import reactor.core.publisher.Mono;

public interface SpellChecker {

    Mono<SpellCheckResponse> checkSpelling(String text);

}
