package ke.co.willythedev.acrolinxspellchecker.service.impl;

import ke.co.willythedev.acrolinxspellchecker.model.LoginCredentials;
import ke.co.willythedev.acrolinxspellchecker.model.LoginResponse;
import ke.co.willythedev.acrolinxspellchecker.security.JwtAuthenticationManager;
import ke.co.willythedev.acrolinxspellchecker.security.TokenProvider;
import ke.co.willythedev.acrolinxspellchecker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationManager authenticationManager;

    @Override
    public Mono<LoginResponse> loginUser(LoginCredentials credentials) {
        if (!StringUtils.hasLength(credentials.getUsername()) || !StringUtils.hasLength(credentials.getPassword())) {
            return Mono.error(new BadCredentialsException("Username and password are required!"));
        }

        Authentication authenticationToken =
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());

        Mono<Authentication> authentication = authenticationManager.authenticate(authenticationToken);

        ReactiveSecurityContextHolder.withAuthentication(authenticationToken);

        return authentication.map(auth -> {
            String jwt = tokenProvider.generateToken(auth);
            return new LoginResponse("Success", "00", jwt);
        });
    }
}
