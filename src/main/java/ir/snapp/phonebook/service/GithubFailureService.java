package ir.snapp.phonebook.service;

import org.springframework.data.domain.Pageable;

/**
 * GithubFailure service
 *
 * @author Pouya Ashna
 */
public interface GithubFailureService {

    void save(Long contactId, String githubRepositoryName, Pageable pageable, String message);
}
