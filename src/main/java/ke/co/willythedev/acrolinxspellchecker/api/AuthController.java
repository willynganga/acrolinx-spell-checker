package ke.co.willythedev.acrolinxspellchecker.api;

import ke.co.willythedev.acrolinxspellchecker.model.LoginCredentials;
import ke.co.willythedev.acrolinxspellchecker.model.LoginResponse;
import ke.co.willythedev.acrolinxspellchecker.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Handles log in for users.
 */
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Mono<LoginResponse> loginUser(
            @RequestBody LoginCredentials credentials) {
        return authService.loginUser(credentials);
    }

}
