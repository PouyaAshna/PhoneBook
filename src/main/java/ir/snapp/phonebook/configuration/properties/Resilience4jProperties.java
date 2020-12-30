package ir.snapp.phonebook.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Map;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "resilience4j")
public class Resilience4jProperties {

    @NotNull
    private CircuitBreaker circuitBreaker;
    @NotNull
    private RateLimit rateLimit;

    @Getter
    @Setter
    public static class CircuitBreaker {
        @NotNull
        private Map<String, CircuitBreakerConfig> instances;

        @Getter
        @Setter
        public static class CircuitBreakerConfig {
            @NotNull
            private Integer failureRateThreshold;
            @NotNull
            private Integer minimumNumberOfCalls;
            @NotNull
            private Integer permittedNumberOfCallsInHalfOpenState;
            @NotNull
            private Integer slidingWindowSize;
            @NotNull
            private Integer slowCallRateThreshold;
            @NotNull
            private Duration slowCallDurationThreshold;
        }
    }

    @Getter
    @Setter
    public static class RateLimit {
        @NotNull
        private Map<String, RateLimitConfig> instances;

        @Getter
        @Setter
        public static class RateLimitConfig {
            @NotNull
            private Integer limitForPeriod;
            @NotNull
            private Duration limitRefreshPeriod;
        }
    }
}
