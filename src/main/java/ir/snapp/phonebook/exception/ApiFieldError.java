package ir.snapp.phonebook.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class ApiFieldError {

    private String objectName;
    private String field;
    private String message;
    private Object rejectedValue;
}
