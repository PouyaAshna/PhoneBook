package ir.snapp.phonebook.exception.dto;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class ApiErrorDTO {
    private HttpStatus status;
    private ZonedDateTime timestamp;
    private String error;
    private String message;
    private String path;
    private Set<ApiFieldErrorDTO> fields;
}
