package ir.snapp.phonebook.service.impl;

import ir.snapp.phonebook.domain.jpa.ContactEntity;
import ir.snapp.phonebook.repository.jpa.ContactRepository;
import ir.snapp.phonebook.service.ContactGithubService;
import ir.snapp.phonebook.service.ContactService;
import ir.snapp.phonebook.service.dto.ContactDTO;
import ir.snapp.phonebook.service.mapper.ContactMapper;
import ir.snapp.phonebook.service.specification.ContactSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * this class manage operation for contact
 *
 * @author Pouya Ashna
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactSpecification contactSpecification;
    private final ContactMapper contactMapper;
    private final ContactGithubService contactGithubService;

    /**
     * This method save contact and after that request to save github repositories for specified github username
     *
     * @param contactDTO input contact
     * @return saved contact
     */
    @Override
    public ContactDTO save(ContactDTO contactDTO) {
        log.debug("{} : Save -> {}", this.getClass().getCanonicalName(), contactDTO);
        ContactEntity contactEntity = this.contactMapper.toEntity(contactDTO);
        contactEntity = this.contactRepository.save(contactEntity);
        this.contactGithubService.save(contactDTO.getGithub(), contactEntity.getId());
        return this.contactMapper.toDTO(contactEntity);
    }

    /**
     * This method search contacts with {@link org.springframework.data.jpa.domain.Specification}
     *
     * @param contactDTO search params
     * @param pageable   page info
     * @return founded contacts
     */
    @Override
    public Page<ContactDTO> search(ContactDTO contactDTO, Pageable pageable) {
        log.debug("{} : Search -> {}", this.getClass().getCanonicalName(), contactDTO);
        return this.contactRepository
                .findAll(this.contactSpecification.getFilter(contactDTO), pageable)
                .map(contactMapper::toDTO);
    }
}
