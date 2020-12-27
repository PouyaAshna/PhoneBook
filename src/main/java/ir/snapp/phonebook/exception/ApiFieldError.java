package ir.snapp.phonebook.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiFieldError {

    private String objectName;
    private String field;
    private String message;
    private Object rejectedValue;
}
