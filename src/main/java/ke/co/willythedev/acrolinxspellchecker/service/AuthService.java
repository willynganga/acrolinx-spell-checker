package ke.co.willythedev.acrolinxspellchecker.service;

import ke.co.willythedev.acrolinxspellchecker.model.LoginCredentials;
import ke.co.willythedev.acrolinxspellchecker.model.LoginResponse;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<LoginResponse> loginUser(LoginCredentials credentials);

}
