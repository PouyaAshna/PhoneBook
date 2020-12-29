package ir.snapp.phonebook.domain.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Document
public class GithubFailureEntity {

    @Id
    private String id;
    private Long contactId;
    private String repositoryName;
    private int pageNumber;
    private int pageSize;
    private String failureMessage;
}
