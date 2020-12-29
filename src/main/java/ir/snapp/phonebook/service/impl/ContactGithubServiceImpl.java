package ir.snapp.phonebook.service.impl;

import ir.snapp.phonebook.domain.jpa.ContactEntity;
import ir.snapp.phonebook.domain.jpa.ContactGithubEntity;
import ir.snapp.phonebook.domain.mongo.GithubFailureEntity;
import ir.snapp.phonebook.repository.api.GithubRepository;
import ir.snapp.phonebook.repository.jpa.ContactGithubRepository;
import ir.snapp.phonebook.repository.mongo.GithubFailureRepository;
import ir.snapp.phonebook.service.ContactGithubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactGithubServiceImpl implements ContactGithubService {

    private final GithubRepository githubRepository;
    private final ContactGithubRepository contactGithubRepository;
    private final GithubFailureRepository githubFailureRepository;

    @Override
    public void save(String githubUsername, Long contactId) {
        log.debug("{} : Save ->  GithubUsername: {}, contactId: {}", this.getClass().getCanonicalName(), githubUsername, contactId);
        this.savePageable(githubUsername, contactId, PageRequest.of(1, 20));
    }

    private void savePageable(String githubUsername, Long contactId, Pageable pageRequest) {
        this.githubRepository
                .findAll(githubUsername, pageRequest)
                .doOnSuccess(githubDTOS -> {
                    if (!githubDTOS.isEmpty()) {
                        ContactEntity contactEntity = new ContactEntity();
                        contactEntity.setId(contactId);
                        Set<ContactGithubEntity> contactGithub = githubDTOS.stream().map(githubDTO -> {
                            ContactGithubEntity contactGithubEntity = new ContactGithubEntity();
                            contactGithubEntity.setRepositoryName(githubDTO.getName());
                            contactGithubEntity.setContact(contactEntity);
                            return contactGithubEntity;
                        }).collect(Collectors.toSet());
                        try {
                            this.contactGithubRepository.saveAll(contactGithub);
                        } catch (DataIntegrityViolationException e) {
                            Set<ContactGithubEntity> savedContactGithub = this.contactGithubRepository.findAllByContactId(contactId);
                            contactGithub.removeAll(savedContactGithub);
                            this.contactGithubRepository.saveAll(contactGithub);
                        }
                        this.savePageable(githubUsername, contactId, pageRequest.next());
                    }
                })
                .doOnError(throwable -> {
                    GithubFailureEntity githubFailureEntity = new GithubFailureEntity();
                    githubFailureEntity.setContactId(contactId);
                    githubFailureEntity.setRepositoryName(githubUsername);
                    githubFailureEntity.setPageNumber(pageRequest.getPageNumber());
                    githubFailureEntity.setPageSize(pageRequest.getPageSize());
                    githubFailureEntity.setFailureMessage(throwable.getMessage());
                    this.githubFailureRepository.save(githubFailureEntity);
                })
                .subscribe();
    }
}
