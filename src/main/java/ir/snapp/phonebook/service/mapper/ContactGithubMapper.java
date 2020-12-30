package ir.snapp.phonebook.service.mapper;

import ir.snapp.phonebook.domain.jpa.ContactGithubEntity;
import ir.snapp.phonebook.service.dto.ContactGithubDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * ContactGithub mapper
 *
 * @author Pouya Ashna
 */
@Mapper(componentModel = "spring")
public interface ContactGithubMapper {

    @Mapping(source = "contact.id", target = "contactId")
    ContactGithubDTO toDTO(ContactGithubEntity entity);
}
