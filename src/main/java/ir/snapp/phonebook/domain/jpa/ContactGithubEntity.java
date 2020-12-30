package ir.snapp.phonebook.domain.jpa;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * Contact Github Domain
 *
 * @author Pouya Ashna
 */
@Data
@Entity
@Table(name = "contact_github", uniqueConstraints =
@UniqueConstraint(name = "UniqueRepoNameAndContact", columnNames = {"repositoryName", "contact_id"}))
public class ContactGithubEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String repositoryName;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ContactEntity contact;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactGithubEntity that = (ContactGithubEntity) o;
        return Objects.equals(repositoryName, that.repositoryName) &&
                Objects.equals(contact.getId(), that.contact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(repositoryName, contact.getId());
    }
}
