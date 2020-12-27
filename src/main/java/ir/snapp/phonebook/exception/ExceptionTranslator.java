package ir.snapp.phonebook.exception;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionTranslator {

    private final HttpServletRequest httpServletRequest;

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException) {
        ApiError.ApiErrorBuilder apiErrorBuilder = ApiError.builder()
                .error("DatabaseException")
                .timestamp(ZonedDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .path(httpServletRequest.getRequestURI());
        Throwable cause = dataIntegrityViolationException.getCause();
        if (cause instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) cause;
            String localizedMessage = constraintViolationException.getCause().getLocalizedMessage();
            if (localizedMessage.startsWith("Duplicate entry")) {
                apiErrorBuilder.message(constraintViolationException.getConstraintName());
            } else {
                apiErrorBuilder.message("UnknownDatabaseException");
            }
        } else {
            apiErrorBuilder.message("UnknownDatabaseException");
        }
        return ResponseEntity.badRequest().body(apiErrorBuilder.build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Set<ApiFieldError> apiFieldErrors = methodArgumentNotValidException
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError ->
                        new ApiFieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue())
                ).collect(Collectors.toSet());
        ApiError.ApiErrorBuilder apiErrorBuilder = ApiError.builder()
                .error("MethodArgumentValidationException")
                .message("ValidationException")
                .timestamp(ZonedDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .fields(apiFieldErrors)
                .path(httpServletRequest.getRequestURI());
        return ResponseEntity.badRequest().body(apiErrorBuilder.build());
    }
}
