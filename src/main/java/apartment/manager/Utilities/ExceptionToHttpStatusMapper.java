package apartment.manager.Utilities;

import jakarta.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ExceptionToHttpStatusMapper {
    private final Map<Class<?extends Exception>, HttpStatus> exceptionToStatusMap = new HashMap<>();

    public ExceptionToHttpStatusMapper(){
        exceptionToStatusMap.put(NoSuchElementException.class, HttpStatus.NOT_FOUND);
        exceptionToStatusMap.put(ValidationException.class, HttpStatus.BAD_REQUEST);
        exceptionToStatusMap.put(ObjectOptimisticLockingFailureException.class, HttpStatus.CONFLICT);
        exceptionToStatusMap.put(AuthenticationCredentialsNotFoundException.class, HttpStatus.UNAUTHORIZED);
        exceptionToStatusMap.put(DataIntegrityViolationException.class, HttpStatus.CONFLICT);
    }
    public HttpStatus getHttpStatus(Class<?extends Exception> exceptionClass){
        return exceptionToStatusMap.get(exceptionClass);
    }
}
