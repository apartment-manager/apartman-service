package apartment.manager.Utilities;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ExceptionToHttpStatusMapper {
    private  Map<Class<?extends Exception>, HttpStatus> exceptionToStatusMap = new HashMap<>();

    public ExceptionToHttpStatusMapper(){
        exceptionToStatusMap.put(NoSuchElementException.class, HttpStatus.NOT_FOUND);
        exceptionToStatusMap.put(ValidationException.class, HttpStatus.BAD_REQUEST);
    }
    public HttpStatus getHttpStatus(Class<?extends Exception> exceptionClass){
        return exceptionToStatusMap.get(exceptionClass);
    }
}
