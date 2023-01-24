package ke.co.willythedev.acrolinxspellchecker.security;

import ke.co.willythedev.acrolinxspellchecker.security.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Converts a server web exchange to authentication.
 */
@AllArgsConstructor
public class TokenAuthenticationConverter implements ServerAuthenticationConverter {
    private static final String BEARER = "Bearer ";
    private static final Predicate<String> matchBearerLength = authValue -> authValue.length() > BEARER.length();
    private static final Function<String, String> isolateBearerValue = authValue -> authValue.substring(BEARER.length(), authValue.length());

    private final TokenProvider tokenProvider;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange)
                .map(SecurityUtils::getTokenFromRequest)
                .filter(Objects::nonNull)
                .filter(matchBearerLength)
                .map(isolateBearerValue)
                .filter(StringUtils::hasLength)
                .map(tokenProvider::getAuthentication)
                .filter(Objects::nonNull);
    }
}
