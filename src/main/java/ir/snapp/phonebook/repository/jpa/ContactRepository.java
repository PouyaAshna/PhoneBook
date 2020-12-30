package ir.snapp.phonebook.repository.jpa;

import ir.snapp.phonebook.domain.jpa.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Contact repository
 *
 * @author Pouya Ashna
 */
@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long>, JpaSpecificationExecutor<ContactEntity> {
}
