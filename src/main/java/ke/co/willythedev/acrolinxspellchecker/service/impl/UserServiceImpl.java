package ke.co.willythedev.acrolinxspellchecker.service.impl;

import ke.co.willythedev.acrolinxspellchecker.exception.UserNotFoundException;
import ke.co.willythedev.acrolinxspellchecker.model.User;
import ke.co.willythedev.acrolinxspellchecker.repository.UserRepository;
import ke.co.willythedev.acrolinxspellchecker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<User> findByUsername(String username) {
        return Mono.fromCallable(() -> userRepository.findByUsername(username).orElse(null))
                .publishOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(new UserNotFoundException(String.format("User %s not found!", username))));
    }

    @Override
    public Mono<User> saveUser(User user) {
        return Mono.fromCallable(() -> userRepository.saveAndFlush(user))
                .publishOn(Schedulers.boundedElastic());
    }
}
