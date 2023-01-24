package ke.co.willythedev.acrolinxspellchecker.security.utils;

import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Provides handy security methods.
 */
@UtilityClass
public class SecurityUtils {

    public static String getTokenFromRequest(ServerWebExchange webExchange) {
        String token = webExchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        return StringUtils.hasLength(token) ? token : "";
    }

    public static Mono<String> getUserFromRequest(ServerWebExchange webExchange) {
        return webExchange.getPrincipal()
                .cast(UsernamePasswordAuthenticationToken.class)
                .map(UsernamePasswordAuthenticationToken::getPrincipal)
                .cast(User.class)
                .map(User::getUsername);
    }

}
