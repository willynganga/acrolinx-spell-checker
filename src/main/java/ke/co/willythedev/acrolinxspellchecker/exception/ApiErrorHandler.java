package ke.co.willythedev.acrolinxspellchecker.exception;

import ke.co.willythedev.acrolinxspellchecker.other.UniversalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Date;

@ControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler(CannotCheckSpellingMistakesException.class)
    public Mono<ResponseEntity<UniversalResponse>> handleCannotCheckSpellingMistakesException(CannotCheckSpellingMistakesException ex) {
        UniversalResponse response = UniversalResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("An IO exception occurred: " + ex.getMessage())
                .data(Collections.emptyMap())
                .timestamp(new Date())
                .build();
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response));
    }

    @ExceptionHandler(NullOrBlankTextException.class)
    public Mono<ResponseEntity<UniversalResponse>> handleCannotCheckSpellingMistakesException(NullOrBlankTextException ex) {
        UniversalResponse response = UniversalResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .data(Collections.emptyMap())
                .timestamp(new Date())
                .build();
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public Mono<ResponseEntity<UniversalResponse>> handleBadCredentials(BadCredentialsException e) {
        UniversalResponse response = UniversalResponse.builder()
                .statusCode(401)
                .message(e.getMessage())
                .build();
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response));
    }

}