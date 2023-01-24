package ke.co.willythedev.acrolinxspellchecker.security;

import ke.co.willythedev.acrolinxspellchecker.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;

/**
 * Responsible for handling user login.
 * Uses the Reactive Authentication Manager.
 */
@Slf4j
@AllArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final ReactiveUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    public JwtAuthenticationManager(ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if (authentication.isAuthenticated()) return Mono.just(authentication);

        return Mono.just(authentication)
                .switchIfEmpty(Mono.defer(this::raiseBadCredentials))
                .cast(UsernamePasswordAuthenticationToken.class)
                .flatMap(this::authenticateToken)
                .publishOn(Schedulers.parallel())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Could not get username from token")))
                .flatMap(u -> userService.findByUsername(u.getUsername()))
                .map(u -> {
                    if (passwordEncoder.matches((String) authentication.getCredentials(), u.getPassword())) return u;

                    int trials = u.getTrials() + 1;
                    u.setTrials(trials);
                    u.setLocked(trials > 3);

                    return u;
                })
                .flatMap(userService::saveUser)
                .map(user -> new UsernamePasswordAuthenticationToken(
                        authentication.getPrincipal(),
                        authentication.getCredentials(),
                        Collections.singleton(new SimpleGrantedAuthority("USER"))
                ));
    }

    private <T> Mono<T> raiseBadCredentials() {
        return Mono.error(new BadCredentialsException("Bad credentials!!"));
    }

    private Mono<UserDetails> authenticateToken(UsernamePasswordAuthenticationToken authenticationToken) {
        String username = authenticationToken.getName();

        log.info("checking authentication for user " + username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("authenticated user " + username + ", setting security context");
            return this.userDetailsService.findByUsername(username);
        }

        return Mono.empty();
    }
}
