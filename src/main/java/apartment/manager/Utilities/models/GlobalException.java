package apartment.manager.Utilities.models;

public class GlobalException extends RuntimeException {
    private Class<? extends Exception> exception;
    private GlobalExceptionCode code;
    public GlobalException (String message, GlobalExceptionCode code, Class<? extends Exception> exceptionType){
        super(message);
        this.exception=exceptionType;
        this.code=code;

    }

    public Class<? extends Exception> getException() {
        return exception;
    }

    public void setException(Class<? extends Exception> exception) {
        this.exception = exception;
    }

    public GlobalExceptionCode getCode() {
        return code;
    }

    public void setCode(GlobalExceptionCode code) {
        this.code = code;
    }
}
