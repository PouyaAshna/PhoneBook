package ir.snapp.phonebook.domain.jpa;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "contact", uniqueConstraints = {@UniqueConstraint(name = "UniqueEmailAndPhoneNumber", columnNames = {"email", "phoneNumber"})})
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = 11)
    private String phoneNumber;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String organization;
    @Column(nullable = false)
    private String github;
}
