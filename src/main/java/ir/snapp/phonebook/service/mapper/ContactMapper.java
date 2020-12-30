package ir.snapp.phonebook.service.mapper;

import ir.snapp.phonebook.domain.jpa.ContactEntity;
import ir.snapp.phonebook.service.dto.ContactDTO;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * Contact mapper
 *
 * @author Pouya Ashna
 */
@Mapper(componentModel = "spring", uses = ContactGithubMapper.class)
public interface ContactMapper {

    ContactDTO toDTO(ContactEntity entity);

    ContactEntity toEntity(ContactDTO dto);

    Set<ContactDTO> toDTO(Set<ContactEntity> entities);
}
