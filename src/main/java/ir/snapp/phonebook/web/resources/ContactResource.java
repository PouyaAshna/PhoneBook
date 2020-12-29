package ir.snapp.phonebook.web.resources;

import ir.snapp.phonebook.configuration.constants.UrlMappings;
import ir.snapp.phonebook.configuration.validation.PersistGroup;
import ir.snapp.phonebook.configuration.validation.SearchGroup;
import ir.snapp.phonebook.service.ContactService;
import ir.snapp.phonebook.service.dto.ContactDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ContactResource {

    private final ContactService contactService;

    @PutMapping(UrlMappings.CONTACT_CREATE)
    public ResponseEntity<ContactDTO> save(@RequestBody @Validated(PersistGroup.class) ContactDTO contactDTO) {
        log.debug("{} : Save -> {}", this.getClass().getCanonicalName(), contactDTO);
        ContactDTO result = this.contactService.save(contactDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping(UrlMappings.CONTACT_SEARCH)
    public ResponseEntity<Page<ContactDTO>> search(@RequestBody @Validated(SearchGroup.class) ContactDTO contactDTO, Pageable pageable) {
        log.debug("{} : Search -> {}", this.getClass().getCanonicalName(), contactDTO);
        Page<ContactDTO> result = this.contactService.search(contactDTO, pageable);
        return ResponseEntity.ok(result);
    }
}
