package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidSizeTypeException extends RuntimeException {
    public InvalidSizeTypeException() {
        super();
    }

    public InvalidSizeTypeException(String message) {
        super(message);
    }

    public InvalidSizeTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSizeTypeException(Throwable cause) {
        super(cause);
    }

    protected InvalidSizeTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
