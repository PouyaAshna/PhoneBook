package ir.snapp.phonebook.domain;

import lombok.Data;

import javax.persistence.*;

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
}
