package ke.co.willythedev.acrolinxspellchecker.service.impl;

import ke.co.willythedev.acrolinxspellchecker.exception.CannotCheckSpellingMistakesException;
import ke.co.willythedev.acrolinxspellchecker.exception.NullOrBlankTextException;
import ke.co.willythedev.acrolinxspellchecker.model.SpellCheckResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SpellCheckerImplTest {
    @Mock
    Logger log;
    @InjectMocks
    SpellCheckerImpl spellCheckerImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckSpelling_throwsNullOrBlankTextException() {
        Mono<SpellCheckResponse> result = spellCheckerImpl.checkSpelling("");

        StepVerifier.create(result)
                .expectError(NullOrBlankTextException.class)
                .verify();
    }

    @Test
    void testCheckSpelling_hasNoIssuesIfTextHasNoSpellingMistakes() {
        Mono<SpellCheckResponse> result = spellCheckerImpl.checkSpelling("Hello, world!");

        StepVerifier.create(result)
                .assertNext(spellCheckResponse -> {
                    Assertions.assertEquals(spellCheckResponse.getIssues().size(), 0);
                    Assertions.assertEquals(spellCheckResponse.getInfo().getWords(), 2);
                })
                .verifyComplete();
    }

    @Test
    void testCheckSpelling_hasIssuesIfTextHasSpellingMistakes() {
        Mono<SpellCheckResponse> result = spellCheckerImpl.checkSpelling("Hello, world! Toaday is an good day!");

        StepVerifier.create(result)
                .assertNext(spellCheckResponse -> {
                    log.info("Asserting....");
                    assertThat(spellCheckResponse.getIssues().size()).isGreaterThan(0);
                })
                .verifyComplete();
    }

    @Test
    void testCheckSpelling_willFindSpellingMistakesForALargePieceOfText() {
        Mono<SpellCheckResponse> result = spellCheckerImpl.checkSpelling("The fog was as thikc as pae sop. This wa a poblem. Gary was driing but couldnt see a ting in frnt of him. He knew he should stop, but he road was narow so if he did, it would be right in the centre of the road. He was suer that aother car wud end up rearending him, so he contiued forwad despite the lack of visibilty. This was an unwise move.");

        StepVerifier.create(result)
                .assertNext(spellCheckResponse -> {
                    log.info("Asserting....");
                    assertThat(spellCheckResponse.getIssues().size()).isGreaterThan(10);
                })
                .verifyComplete();
    }


}