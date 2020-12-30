package ir.snapp.phonebook.exception.translator;

import ir.snapp.phonebook.exception.dto.ApiErrorDTO;
import ir.snapp.phonebook.exception.dto.ApiFieldErrorDTO;
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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class translate exception to {@link ApiErrorDTO}
 *
 * @author Pouya Ashna
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionTranslator {

    private final HttpServletRequest httpServletRequest;

    /**
     * Translate database exception
     *
     * @param dataIntegrityViolationException database exception
     * @return translated exception
     */
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorDTO> handleDataIntegrityViolationException(
            DataIntegrityViolationException dataIntegrityViolationException) {
        ApiErrorDTO.ApiErrorDTOBuilder apiErrorBuilder = ApiErrorDTO.builder()
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

    /**
     * Translate validation exception of method arguments
     *
     * @param methodArgumentNotValidException validation exception
     * @return translated exception
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        Set<ApiFieldErrorDTO> apiFieldErrorDTOS = methodArgumentNotValidException
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError ->
                        new ApiFieldErrorDTO(
                                fieldError.getObjectName(),
                                fieldError.getField(),
                                fieldError.getDefaultMessage(),
                                fieldError.getRejectedValue()
                        )
                ).collect(Collectors.toSet());
        ApiErrorDTO.ApiErrorDTOBuilder apiErrorBuilder = ApiErrorDTO.builder()
                .error("MethodArgumentValidationException")
                .message("ValidationException")
                .timestamp(ZonedDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .fields(apiFieldErrorDTOS)
                .path(httpServletRequest.getRequestURI());
        return ResponseEntity.badRequest().body(apiErrorBuilder.build());
    }
}
