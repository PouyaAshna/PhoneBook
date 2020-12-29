package ir.snapp.phonebook.service.impl;

import ir.snapp.phonebook.repository.api.GithubRepository;
import ir.snapp.phonebook.service.ContactGithubService;
import ir.snapp.phonebook.service.GithubFailureService;
import ir.snapp.phonebook.service.GithubService;
import ir.snapp.phonebook.service.dto.GithubDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubServiceImpl implements GithubService {

    private final GithubRepository githubRepository;
    private final GithubFailureService githubFailureService;

    @Override
    public void findAll(String githubUsername, Long contactId, Pageable pageable, Consumer<List<String>> githubResponseConsumer) {
        log.debug("{} : FindAll ->  GithubUsername: {}, contactId: {}, Page: {}", this.getClass().getCanonicalName(), githubUsername, contactId, pageable);
        this.githubRepository
                .findAll(githubUsername, pageable)
                .doOnSuccess(githubDTOS -> {
                    if (!githubDTOS.isEmpty()) {
                        List<String> githubRepositoryNames = githubDTOS.stream()
                                .map(GithubDTO::getName)
                                .collect(Collectors.toList());
                        githubResponseConsumer.accept(githubRepositoryNames);
                        this.findAll(githubUsername, contactId, pageable.next(), githubResponseConsumer);
                    }
                })
                .doOnError(throwable -> this.githubFailureService.save(contactId, githubUsername, pageable, throwable.getMessage()))
                .subscribe();
    }
}
