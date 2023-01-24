package ke.co.willythedev.acrolinxspellchecker.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Verifies the headers for incoming requests.
 */
public class JwtHeaderExchangeMatcher implements ServerWebExchangeMatcher {

    @Override
    public Mono<MatchResult> matches(ServerWebExchange exchange) {
        Mono<ServerHttpRequest> request = Mono.just(exchange)
                .map(ServerWebExchange::getRequest);

        return request.map(ServerHttpRequest::getHeaders)
                .filter(h -> h.containsKey(HttpHeaders.AUTHORIZATION))
                .flatMap(h -> MatchResult.match())
                .switchIfEmpty(MatchResult.notMatch());
    }
}
