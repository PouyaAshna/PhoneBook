package ir.snapp.phonebook.configuration.resiliency;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * This class manage how to initialize rate limit and config it for more information about RateLimit see
 * {@link RateLimiter} documentation
 *
 * @author Pouya Ashna
 */
@Configuration
public class RateLimitConfiguration {

    /**
     * Initialize rate limit for github
     * Github limits un authenticated requests to 60 api call per hour
     *
     * @return rate limit instance
     */
    @Bean
    public RateLimiter githubRateLimiter() {
        RateLimiterConfig rateLimiterConfig = RateLimiterConfig
                .custom()
                .limitForPeriod(60)
                .limitRefreshPeriod(Duration.ofHours(1))
                .build();
        return RateLimiter.of("github", rateLimiterConfig);
    }
}
