package kg.attractor.jobsearch.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String avatarDir;
    private Locale locale;

    @Setter
    @Getter
    public static class Locale {
        private String defaultLang;
        private String paramName;
    }
}