package ke.co.willythedev.acrolinxspellchecker.security.user;

import ke.co.willythedev.acrolinxspellchecker.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * An implementation of ReactiveUserDetails.
 */
@Slf4j
@Component
@AllArgsConstructor
public class ReactiveUserDetailsImpl implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.fromCallable(() -> userRepository.findByUsername(username).orElse(null))
                .publishOn(Schedulers.boundedElastic())
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new BadCredentialsException("No user with provided username found!")))
                .map(this::createSpringSecurityUser);
    }

    private User createSpringSecurityUser(ke.co.willythedev.acrolinxspellchecker.model.User user) {
        List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
