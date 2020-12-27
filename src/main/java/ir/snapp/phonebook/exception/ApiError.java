package ir.snapp.phonebook.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private HttpStatus status;
    private ZonedDateTime timestamp;
    private String error;
    private String message;
    private String path;
    private Set<ApiFieldError> fields;
}
