package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException() {
        super();
    }

    public InvalidUsernameException(String message) {
        super(message);
    }

    public InvalidUsernameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUsernameException(Throwable cause) {
        super(cause);
    }

    protected InvalidUsernameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
