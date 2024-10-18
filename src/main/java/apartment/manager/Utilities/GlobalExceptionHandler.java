package apartment.manager.Utilities;

import apartment.manager.Utilities.models.GlobalException;
import apartment.manager.Utilities.models.GlobalExceptionCode;
import apartment.manager.presentation.models.ErrorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    ExceptionToHttpStatusMapper exceptionToHttpStatusMapper;
/// TODO: handle version exceptions
    /**
     * Exception handler for {@link MethodArgumentNotValidException} This kind of exceptions is caused by violations to the validation Criteria defined by the
     * Annotations like {{@link jakarta.validation.constraints.NotBlank}}
     *
     * @param ex      the exception to handle
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     */
    @Override
    public final ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder errors = new StringBuilder("The provided model has the following problems: {");
        ex.getAllErrors().forEach(error ->
                errors.append(error.getDefaultMessage()).append(" ,")
        );
        errors.delete(errors.length() - 2, errors.length());
        errors.append("}");
        return createErrorResponse(errors.toString(), HttpStatus.BAD_REQUEST, GlobalExceptionCode.VALIDATION, request);
    }

    @ExceptionHandler(GlobalException.class)
    private final ResponseEntity<Object> handleException(GlobalException exception, WebRequest request) {
        HttpStatus httpStatus = exceptionToHttpStatusMapper.getHttpStatus(exception.getException());
        return createErrorResponse(exception.getMessage(), httpStatus, exception.getCode(), request);
    }

    private ResponseEntity<Object> createErrorResponse(String message, HttpStatus httpStatus, GlobalExceptionCode errorCode, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(message, errorCode, new Date());
        return createResponseEntity(errorDetails, null, httpStatus, request);
    }
}
