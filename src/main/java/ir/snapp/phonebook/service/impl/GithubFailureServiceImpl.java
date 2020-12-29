package ir.snapp.phonebook.service.impl;

import ir.snapp.phonebook.domain.mongo.GithubFailureEntity;
import ir.snapp.phonebook.repository.mongo.GithubFailureRepository;
import ir.snapp.phonebook.service.GithubFailureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubFailureServiceImpl implements GithubFailureService {

    private final GithubFailureRepository githubFailureRepository;

    @Override
    public void save(Long contactId, String githubRepositoryName, Pageable pageable, String message) {
        log.debug("{} : Save -> ContactId : {}, GithubRepositoryName : {}", this.getClass().getCanonicalName(), contactId, githubRepositoryName);
        GithubFailureEntity githubFailureEntity = new GithubFailureEntity();
        githubFailureEntity.setContactId(contactId);
        githubFailureEntity.setRepositoryName(githubRepositoryName);
        githubFailureEntity.setPageNumber(pageable.getPageNumber());
        githubFailureEntity.setPageSize(pageable.getPageSize());
        githubFailureEntity.setFailureMessage(message);
        this.githubFailureRepository.save(githubFailureEntity);
    }
}
