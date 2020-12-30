package ir.snapp.phonebook.configuration.resiliency;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import ir.snapp.phonebook.configuration.properties.Resilience4jProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * This class manage how to initialize rate limit and config it for more information about RateLimit see
 * {@link RateLimiter} documentation
 *
 * @author Pouya Ashna
 */
@Configuration
@RequiredArgsConstructor
public class RateLimitConfiguration {

    private final Resilience4jProperties resilience4jProperties;

    /**
     * Initialize rate limit for github
     * Github limits un authenticated requests to 60 api call per hour
     *
     * @return rate limit instance
     */
    @Bean
    public RateLimiter githubRateLimiter() {
        Map<String, Resilience4jProperties.RateLimit.RateLimitConfig> rateLimitInstances =
                resilience4jProperties.getRateLimit().getInstances();
        if (rateLimitInstances.containsKey("github")) {
            Resilience4jProperties.RateLimit.RateLimitConfig rateLimitConfig = rateLimitInstances.get("github");
            RateLimiterConfig rateLimiterConfig = RateLimiterConfig
                    .custom()
                    .limitForPeriod(rateLimitConfig.getLimitForPeriod())
                    .limitRefreshPeriod(rateLimitConfig.getLimitRefreshPeriod())
                    .build();
            return RateLimiter.of("github", rateLimiterConfig);
        } else {
            throw new RuntimeException("resilience4j rateLimit github instance should set");
        }
    }
}
