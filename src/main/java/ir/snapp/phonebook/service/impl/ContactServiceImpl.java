package ir.snapp.phonebook.service.impl;

import ir.snapp.phonebook.domain.ContactEntity;
import ir.snapp.phonebook.repository.jpa.ContactRepository;
import ir.snapp.phonebook.service.ContactService;
import ir.snapp.phonebook.service.dto.ContactDTO;
import ir.snapp.phonebook.service.mapper.ContactMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Override
    public ContactDTO save(ContactDTO contactDTO) {
        log.debug("{} : Save -> {}", this.getClass().getCanonicalName(), contactDTO);
        ContactEntity contactEntity = this.contactMapper.toEntity(contactDTO);
        contactEntity = this.contactRepository.save(contactEntity);
        return this.contactMapper.toDTO(contactEntity);
    }
}
