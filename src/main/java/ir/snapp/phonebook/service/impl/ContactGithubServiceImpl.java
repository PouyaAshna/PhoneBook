package ir.snapp.phonebook.service.impl;

import ir.snapp.phonebook.domain.ContactEntity;
import ir.snapp.phonebook.domain.ContactGithubEntity;
import ir.snapp.phonebook.repository.api.GithubRepository;
import ir.snapp.phonebook.repository.jpa.ContactGithubRepository;
import ir.snapp.phonebook.service.ContactGithubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactGithubServiceImpl implements ContactGithubService {

    private final GithubRepository githubRepository;
    private final ContactGithubRepository contactGithubRepository;

    @Override
    public void save(String githubUsername, Long userId) {
        log.debug("{} : Save ->  GithubUsername: {}, userId: {}", this.getClass().getCanonicalName(), githubUsername, userId);
        this.savePageable(githubUsername, userId, PageRequest.of(1, 20));
    }

    private void savePageable(String githubUsername, Long userId, Pageable pageRequest) {
        this.githubRepository
                .findAll(githubUsername, pageRequest)
                .doOnSuccess(githubDTOS -> {
                    if (!githubDTOS.isEmpty()) {
                        ContactEntity contactEntity = new ContactEntity();
                        contactEntity.setId(userId);
                        List<ContactGithubEntity> contactGithub = githubDTOS.stream().map(githubDTO -> {
                            ContactGithubEntity contactGithubEntity = new ContactGithubEntity();
                            contactGithubEntity.setRepositoryName(githubDTO.getName());
                            contactGithubEntity.setContact(contactEntity);
                            return contactGithubEntity;
                        }).collect(Collectors.toList());
                        this.contactGithubRepository.saveAll(contactGithub);
                        this.savePageable(githubUsername, userId, pageRequest.next());
                    }
                })
                .doOnError(throwable -> {
                    //TODO insert into mongo to schedule for re-call
                })
                .subscribe();
    }
}
