package ir.snapp.phonebook.repository.jpa;

import ir.snapp.phonebook.domain.jpa.ContactGithubEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ContactGithubRepository extends JpaRepository<ContactGithubEntity, Long> {

    Set<ContactGithubEntity> findAllByContactId(Long contactId);
}
