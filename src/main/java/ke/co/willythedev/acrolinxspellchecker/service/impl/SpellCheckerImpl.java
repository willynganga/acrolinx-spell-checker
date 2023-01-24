package ke.co.willythedev.acrolinxspellchecker.service.impl;

import ke.co.willythedev.acrolinxspellchecker.exception.CannotCheckSpellingMistakesException;
import ke.co.willythedev.acrolinxspellchecker.exception.NullOrBlankTextException;
import ke.co.willythedev.acrolinxspellchecker.model.Info;
import ke.co.willythedev.acrolinxspellchecker.model.Issue;
import ke.co.willythedev.acrolinxspellchecker.model.Match;
import ke.co.willythedev.acrolinxspellchecker.model.SpellCheckResponse;
import ke.co.willythedev.acrolinxspellchecker.service.SpellChecker;
import lombok.extern.slf4j.Slf4j;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.SuggestedReplacement;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SpellCheckerImpl implements SpellChecker {

    @Override
    public Mono<SpellCheckResponse> checkSpelling(String text) {
        if (text == null || text.isBlank())
            return Mono.error(new NullOrBlankTextException("The text cannot be null or blank."));

        Date start = new Date();
        StringTokenizer tokenizer = new StringTokenizer(text);
        StringBuffer stringBuffer = new StringBuffer(text);

        return Mono.just(text)
                .publishOn(Schedulers.boundedElastic())
                .mapNotNull(this::checkSpellingMistakes)
                .switchIfEmpty(throwIoException())
                .flatMapMany(Flux::fromIterable)
                .map(match -> mapMatchToIssue(stringBuffer, match))
                .collectList()
                .map(issues -> mapToSpellCheckResponse(start, tokenizer, issues));
    }

    public SpellCheckResponse mapToSpellCheckResponse(Date start, StringTokenizer tokenizer, List<Issue> issues) {
        return SpellCheckResponse.builder()
                .id(UUID.randomUUID().toString())
                .issues(issues)
                .info(Info.builder()
                        .words(tokenizer.countTokens())
                        .time(start.toString())
                        .build())
                .build();
    }

    private Issue mapMatchToIssue(StringBuffer stringBuffer, RuleMatch match) {
        log.info("Mapping match to issue...");

        List<String> suggestions = match.getSuggestedReplacementObjects()
                .stream()
                .map(SuggestedReplacement::getReplacement)
                .limit(5)
                .collect(Collectors.toUnmodifiableList());

        return Issue.builder()
                .type("spelling")
                .match(Match.builder()
                        .surface(stringBuffer.substring(match.getFromPos(), match.getToPos()))
                        .beginOffset(match.getFromPos())
                        .endOffset(match.getToPos())
                        .replacement(suggestions)
                        .build())
                .build();
    }

    private <T> Mono<? extends T> throwIoException() {
        return Mono.error(new CannotCheckSpellingMistakesException("Cannot spell check text. Make sure to provide correct data."));
    }

    private List<RuleMatch> checkSpellingMistakes(String text) {
        log.info("Checking for spelling mistakes:::");
        JLanguageTool langTool = new JLanguageTool(new BritishEnglish());

        try {
            return langTool.check(text);
        } catch (IOException e) {
            log.error("IOException::: {}", e.getMessage());
            return null;
        }
    }

}
