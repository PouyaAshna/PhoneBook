package ir.snapp.phonebook.configuration;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimitConfiguration {

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
