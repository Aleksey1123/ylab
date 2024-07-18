package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
public class InvalidResponseBodyException extends RuntimeException {
    public InvalidResponseBodyException() {
        super();
    }

    public InvalidResponseBodyException(String message) {
        super(message);
    }

    public InvalidResponseBodyException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidResponseBodyException(Throwable cause) {
        super(cause);
    }

    protected InvalidResponseBodyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
