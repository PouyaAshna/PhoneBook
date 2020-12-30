package ir.snapp.phonebook.configuration.resiliency;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import ir.snapp.phonebook.configuration.properties.Resilience4jProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;

/**
 * This class manage how to initialize circuit breaker and config it for more information about CircuitBreaker see
 * {@link CircuitBreaker} documentation
 *
 * @author Pouya Ashna
 */
@Configuration
@RequiredArgsConstructor
public class CircuitBreakerConfiguration {

    private final Resilience4jProperties resilience4jProperties;

    /**
     * Initialize circuit breaker for github
     *
     * @return circuit breaker instance
     */
    @Bean
    public CircuitBreaker githubCircuitBreaker() {
        Map<String, Resilience4jProperties.CircuitBreaker.CircuitBreakerConfig> circuitBreakerInstances =
                resilience4jProperties.getCircuitBreaker().getInstances();
        if (circuitBreakerInstances.containsKey("github")) {
            Resilience4jProperties.CircuitBreaker.CircuitBreakerConfig githubCircuitBreakerConfig = circuitBreakerInstances.get("github");
            CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                    .failureRateThreshold(githubCircuitBreakerConfig.getFailureRateThreshold())
                    .minimumNumberOfCalls(githubCircuitBreakerConfig.getMinimumNumberOfCalls())
                    .permittedNumberOfCallsInHalfOpenState(githubCircuitBreakerConfig.getPermittedNumberOfCallsInHalfOpenState())
                    .slidingWindowSize(githubCircuitBreakerConfig.getSlidingWindowSize())
                    .slowCallRateThreshold(githubCircuitBreakerConfig.getSlowCallRateThreshold())
                    .slowCallDurationThreshold(githubCircuitBreakerConfig.getSlowCallDurationThreshold())
                    .recordExceptions(Exception.class)
                    .build();
            return CircuitBreaker.of("github", circuitBreakerConfig);
        } else {
            throw new RuntimeException("resilience4j circuitBreaker github instance should set");
        }
    }
}
