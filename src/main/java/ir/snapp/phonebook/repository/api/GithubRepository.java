package ir.snapp.phonebook.repository.api;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.reactor.ratelimiter.operator.RateLimiterOperator;
import ir.snapp.phonebook.service.dto.GithubDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Github Repository
 *
 * @author Pouya Ashna
 */
@Repository
@RequiredArgsConstructor
public class GithubRepository {

    private final WebClient webClient;
    @Qualifier("githubRateLimiter")
    private final RateLimiter rateLimiter;
    @Qualifier("githubCircuitBreaker")
    private final CircuitBreaker circuitBreaker;

    /**
     * This method create api call to fetch github repositories
     *
     * @param githubUserName github username
     * @param pageable       page info
     * @return founded repositories
     */
    public Mono<List<GithubDTO>> findAll(String githubUserName, Pageable pageable) {
        return webClient
                .get()
                .uri("https://api.github.com/users/{username}/repos?page={pageNumber}&per_page={pageSize}",
                        githubUserName, pageable.getPageNumber(), pageable.getPageSize())
                .retrieve()
                .bodyToFlux(GithubDTO.class)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .transform(RateLimiterOperator.of(rateLimiter))
                .collectList();
    }
}
