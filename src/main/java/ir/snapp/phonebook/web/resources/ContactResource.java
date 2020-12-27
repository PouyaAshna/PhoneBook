package ir.snapp.phonebook.web.resources;

import ir.snapp.phonebook.configuration.constants.UrlMappings;
import ir.snapp.phonebook.service.ContactService;
import ir.snapp.phonebook.service.dto.ContactDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(UrlMappings.CONTACT)
@RequiredArgsConstructor
public class ContactResource {

    private final ContactService contactService;

    @PutMapping
    public ResponseEntity<ContactDTO> save(@RequestBody @Valid ContactDTO contactDTO) {
        log.debug("{} : Save -> {}", this.getClass().getCanonicalName(), contactDTO);
        ContactDTO result = this.contactService.save(contactDTO);
        return ResponseEntity.ok(result);
    }
}
