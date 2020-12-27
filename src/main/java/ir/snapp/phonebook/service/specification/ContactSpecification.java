package ir.snapp.phonebook.service.specification;

import ir.snapp.phonebook.domain.ContactEntity;
import ir.snapp.phonebook.service.dto.ContactDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ContactSpecification {

    public Specification<ContactEntity> getFilter(ContactDTO contactDTO) {
        return Specification
                .where(SpecificationUtil.<ContactEntity>nameAttributeContains(ContactDTO.Fields.name, contactDTO.getName()))
                .and(SpecificationUtil.nameAttributeContains(ContactDTO.Fields.email, contactDTO.getEmail()))
                .and(SpecificationUtil.nameAttributeContains(ContactDTO.Fields.phoneNumber, contactDTO.getPhoneNumber()))
                .and(SpecificationUtil.nameAttributeContains(ContactDTO.Fields.organization, contactDTO.getOrganization()))
                .and(SpecificationUtil.nameAttributeContains(ContactDTO.Fields.github, contactDTO.getGithub()))
                ;
    }

}
