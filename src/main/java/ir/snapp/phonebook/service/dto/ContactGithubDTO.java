package ir.snapp.phonebook.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactGithubDTO {

    private Long id;
    private String repositoryName;
    private Long contactId;
}
