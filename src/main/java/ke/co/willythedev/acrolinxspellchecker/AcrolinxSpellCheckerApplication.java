package ke.co.willythedev.acrolinxspellchecker;

import ke.co.willythedev.acrolinxspellchecker.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = AppProperties.class)
public class AcrolinxSpellCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcrolinxSpellCheckerApplication.class, args);
    }

}
