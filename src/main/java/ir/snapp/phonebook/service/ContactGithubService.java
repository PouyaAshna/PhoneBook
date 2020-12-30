package ir.snapp.phonebook.service;

import java.util.List;

/**
 * ContactGithub service
 *
 * @author Pouya Ashna
 */
public interface ContactGithubService {

    void save(String githubUsername, Long contactId);

    void saveAllRepositories(List<String> githubRepositoryNames, Long contactId);
}
