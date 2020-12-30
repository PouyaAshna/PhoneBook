package ir.snapp.phonebook.repository.jpa;

import ir.snapp.phonebook.domain.jpa.ContactGithubEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * ContactGithub repository
 *
 * @author Pouya Ashna
 */
@Repository
public interface ContactGithubRepository extends JpaRepository<ContactGithubEntity, Long> {

    /**
     * this method return all contact github records by contact id
     *
     * @param contactId contactId
     * @return founded contact github records
     */
    Set<ContactGithubEntity> findAllByContactId(Long contactId);
}
