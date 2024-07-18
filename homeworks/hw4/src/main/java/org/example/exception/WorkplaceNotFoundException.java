package org.example.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WorkplaceNotFoundException extends RuntimeException {
    public WorkplaceNotFoundException() {
        super();
    }

    public WorkplaceNotFoundException(String message) {
        super(message);
    }

    public WorkplaceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkplaceNotFoundException(Throwable cause) {
        super(cause);
    }

    protected WorkplaceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
