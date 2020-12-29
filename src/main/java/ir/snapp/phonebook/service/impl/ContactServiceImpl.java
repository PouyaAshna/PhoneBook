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

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactSpecification contactSpecification;
    private final ContactMapper contactMapper;
    private final ContactGithubService contactGithubService;

    @Override
    public ContactDTO save(ContactDTO contactDTO) {
        log.debug("{} : Save -> {}", this.getClass().getCanonicalName(), contactDTO);
        ContactEntity contactEntity = this.contactMapper.toEntity(contactDTO);
        contactEntity = this.contactRepository.save(contactEntity);
        this.contactGithubService.save(contactDTO.getGithub(), contactEntity.getId());
        return this.contactMapper.toDTO(contactEntity);
    }

    @Override
    public Page<ContactDTO> search(ContactDTO contactDTO, Pageable pageable) {
        log.debug("{} : Search -> {}", this.getClass().getCanonicalName(), contactDTO);
        return this.contactRepository
                .findAll(this.contactSpecification.getFilter(contactDTO), pageable)
                .map(contactMapper::toDTO);
    }
}
