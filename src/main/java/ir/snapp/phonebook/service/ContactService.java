package ir.snapp.phonebook.service;

import ir.snapp.phonebook.service.dto.ContactDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Contact service
 *
 * @author Pouya Ashna
 */
public interface ContactService {

    ContactDTO save(ContactDTO contactDTO);

    Page<ContactDTO> search(ContactDTO contactDTO, Pageable pageable);
}
