package ir.snapp.phonebook.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 * ApiFieldError data transfer object
 *
 * @author Pouya Ashna
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class ApiFieldErrorDTO {

    private String objectName;
    private String field;
    private String message;
    private Object rejectedValue;
}
