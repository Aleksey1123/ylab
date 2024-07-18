package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class InvalidResourceTypeException extends RuntimeException {
    public InvalidResourceTypeException() {
        super();
    }

    public InvalidResourceTypeException(String message) {
        super(message);
    }

    public InvalidResourceTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidResourceTypeException(Throwable cause) {
        super(cause);
    }

    protected InvalidResourceTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
