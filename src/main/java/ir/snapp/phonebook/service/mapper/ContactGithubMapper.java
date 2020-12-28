package ir.snapp.phonebook.service.mapper;

import ir.snapp.phonebook.domain.ContactGithubEntity;
import ir.snapp.phonebook.service.dto.ContactGithubDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ContactGithubMapper {

    @Mapping(source = "contact.id", target = "contactId")
    ContactGithubDTO toDTO(ContactGithubEntity entity);

    Set<ContactGithubDTO> toDTO(Set<ContactGithubEntity> entities);
}
