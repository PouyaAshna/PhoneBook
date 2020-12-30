package ir.snapp.phonebook.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "webclient")
public class WebClientProperties {

    @NotNull
    private HttpClient httpClient;

    @Getter
    @Setter
    public static class HttpClient {
        @NotNull
        private Integer connectTimeout;
        @NotNull
        private Integer readTimeout;
        @NotNull
        private Integer writeTimeout;
    }
}
