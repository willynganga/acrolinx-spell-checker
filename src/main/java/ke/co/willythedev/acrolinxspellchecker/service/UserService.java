package ke.co.willythedev.acrolinxspellchecker.service;

import ke.co.willythedev.acrolinxspellchecker.model.User;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> findByUsername(String username);

    Mono<User> saveUser(User user);
}
