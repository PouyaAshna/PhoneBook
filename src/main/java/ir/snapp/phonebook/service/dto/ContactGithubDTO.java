package ir.snapp.phonebook.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ContactGithub data transfer object
 *
 * @author Pouya Ashna
 */
@Data
@NoArgsConstructor
public class ContactGithubDTO {

    private Long id;
    private String repositoryName;
    private Long contactId;
}
