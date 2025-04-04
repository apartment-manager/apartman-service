package apartment.manager.presentation.models;

import apartment.manager.Utilities.models.GlobalExceptionCode;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ErrorDetails {
    private String message;
    private GlobalExceptionCode errorCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE MMM dd HH:mm:ss zzz yyyy")
    private Date timeStamp;

    public ErrorDetails(String message, GlobalExceptionCode errorCode, Date timeStamp) {
        this.message = message;
        this.errorCode = errorCode;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;

    }

    public GlobalExceptionCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(GlobalExceptionCode errorCode) {
        this.errorCode = errorCode;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
