package ir.snapp.phonebook.configuration.resiliency;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * This class manage how to initialize circuit breaker and config it for more information about CircuitBreaker see
 * {@link CircuitBreaker} documentation
 *
 * @author Pouya Ashna
 */
@Configuration
public class CircuitBreakerConfiguration {

    /**
     * Initialize circuit breaker for github
     *
     * @return circuit breaker instance
     */
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
