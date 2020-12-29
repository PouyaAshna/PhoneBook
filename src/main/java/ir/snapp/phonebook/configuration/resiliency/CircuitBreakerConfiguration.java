package ir.snapp.phonebook.configuration.resiliency;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfiguration {

    @Bean
    public CircuitBreaker githubCircuitBreaker() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(10)
                .minimumNumberOfCalls(1)
                .permittedNumberOfCallsInHalfOpenState(1)
                .slidingWindowSize(2)
                .slowCallRateThreshold(10)
                .slowCallDurationThreshold(Duration.ofSeconds(5))
                .recordExceptions(Exception.class)
                .build();
        return CircuitBreaker.of("github", circuitBreakerConfig);
    }
}
