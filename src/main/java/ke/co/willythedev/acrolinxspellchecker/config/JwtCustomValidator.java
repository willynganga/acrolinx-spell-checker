package ke.co.willythedev.acrolinxspellchecker.config;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.Objects;

public class JwtCustomValidator implements OAuth2TokenValidator<Jwt> {
    OAuth2Error error = new OAuth2Error("invalid_token", "Expired token!", null);

    @Override
    public OAuth2TokenValidatorResult validate(Jwt token) {
        if (Objects.requireNonNull(token.getExpiresAt()).isAfter(Instant.now())) {
            return OAuth2TokenValidatorResult.success();
        }

        return OAuth2TokenValidatorResult.failure(error);
    }
}
