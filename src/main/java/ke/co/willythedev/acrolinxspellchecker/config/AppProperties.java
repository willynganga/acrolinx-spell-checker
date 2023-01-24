package ke.co.willythedev.acrolinxspellchecker.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * A spring configuration properties class.
 * Useful for avoiding @Value annotation.
 */
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Auth auth = new Auth();

    /**
     * Is inflated by Spring.
     * Configuration properties in Yaml file are inflated
     * here.
     */
    @Getter
    @Setter
    public static class Auth {
        private String tokenSecret;
        private long tokenExpiry;
    }

    public Auth getAuth() {
        return auth;
    }
}
