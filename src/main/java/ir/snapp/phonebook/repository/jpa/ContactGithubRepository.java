package ir.snapp.phonebook.repository.jpa;

import ir.snapp.phonebook.domain.ContactGithubEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactGithubRepository extends JpaRepository<ContactGithubEntity, Long> {
}
