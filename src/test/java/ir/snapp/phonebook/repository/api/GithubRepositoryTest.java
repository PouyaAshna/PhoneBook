package ir.snapp.phonebook.repository.api;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import ir.snapp.phonebook.service.dto.GithubDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GithubRepositoryTest {


    private GithubRepository githubRepository;

    @Before
    public void setup() {
        githubRepository = mock(GithubRepository.class);
    }

    @Test
    public void success() {
        List<GithubDTO> githubDTOS = new ArrayList<>();
        GithubDTO githubDTO = new GithubDTO();
        githubDTO.setName("test");
        githubDTOS.add(githubDTO);
        when(githubRepository.findAll(any(), any())).thenReturn(Mono.just(githubDTOS));
        Mono<List<GithubDTO>> test = this.githubRepository.findAll("test", PageRequest.of(1, 20));
        Mono<List<GithubDTO>> test2 = this.githubRepository.findAll("test", PageRequest.of(1, 20));
        System.out.println("hit");
    }
}
