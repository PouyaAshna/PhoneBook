package ir.snapp.phonebook.service.impl;

import ir.snapp.phonebook.domain.jpa.ContactEntity;
import ir.snapp.phonebook.domain.jpa.ContactGithubEntity;
import ir.snapp.phonebook.repository.jpa.ContactGithubRepository;
import ir.snapp.phonebook.service.ContactGithubService;
import ir.snapp.phonebook.service.GithubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class manage contact github repositories
 *
 * @author Pouya Ashna
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContactGithubServiceImpl implements ContactGithubService {

    private final ContactGithubRepository contactGithubRepository;
    private final GithubService githubService;

    /**
     * This method fetch all repositories for a specific user from github and after that save them with
     * {@link #saveAllRepositories(List, Long)} ()}
     *
     * @param githubUsername github username for contact
     * @param contactId      specific contact id
     */
    @Override
    public void save(String githubUsername, Long contactId) {
        log.debug("{} : Save ->  GithubUsername: {}, contactId: {}",
                this.getClass().getCanonicalName(), githubUsername, contactId);
        this.githubService.findAll(githubUsername, contactId, PageRequest.of(1, 20), githubRepositoryNames ->
                this.saveAllRepositories(githubRepositoryNames, contactId));
    }

    /**
     * This method save all repositories for a specific user and handle if some repositories exists in database
     *
     * @param githubRepositoryNames list of repositoryNames
     * @param contactId             specific contact id
     */
    @Override
    public void saveAllRepositories(List<String> githubRepositoryNames, Long contactId) {
        log.debug("{} : SaveAllRepositories -> ContactId : {}, {} ",
                this.getClass().getCanonicalName(), contactId, githubRepositoryNames);
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setId(contactId);
        Set<ContactGithubEntity> contactGithub = githubRepositoryNames.stream().map(githubRepositoryName -> {
            ContactGithubEntity contactGithubEntity = new ContactGithubEntity();
            contactGithubEntity.setRepositoryName(githubRepositoryName);
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
    }

}
