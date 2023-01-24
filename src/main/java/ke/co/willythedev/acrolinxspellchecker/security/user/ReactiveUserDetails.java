package ke.co.willythedev.acrolinxspellchecker.security.user;

import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

/**
 * An interface for providing spring security user details.
 */
public interface ReactiveUserDetails {
    Mono<UserDetails> findByUsername(String username);
}
