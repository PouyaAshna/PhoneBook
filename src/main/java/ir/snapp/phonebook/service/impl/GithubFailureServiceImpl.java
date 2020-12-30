package ir.snapp.phonebook.service.impl;

import ir.snapp.phonebook.domain.mongo.GithubFailureEntity;
import ir.snapp.phonebook.repository.mongo.GithubFailureRepository;
import ir.snapp.phonebook.service.GithubFailureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * This class manage operation for github failure
 *
 * @author Pouya Ashna
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GithubFailureServiceImpl implements GithubFailureService {

    private final GithubFailureRepository githubFailureRepository;

    /**
     * This method save github failure request information
     *
     * @param contactId      contactId
     * @param githubUsername github username
     * @param pageable       page info
     * @param message        failure message
     */
    @Override
    public void save(Long contactId, String githubUsername, Pageable pageable, String message) {
        log.debug("{} : Save -> ContactId : {}, GithubUsername : {}",
                this.getClass().getCanonicalName(), contactId, githubUsername);
        GithubFailureEntity githubFailureEntity = new GithubFailureEntity();
        githubFailureEntity.setContactId(contactId);
        githubFailureEntity.setGithubUsername(githubUsername);
        githubFailureEntity.setPageNumber(pageable.getPageNumber());
        githubFailureEntity.setPageSize(pageable.getPageSize());
        githubFailureEntity.setFailureMessage(message);
        this.githubFailureRepository.save(githubFailureEntity);
    }
}
