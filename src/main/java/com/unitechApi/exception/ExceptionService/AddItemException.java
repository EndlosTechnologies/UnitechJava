package com.unitechApi.exception.ExceptionService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)
public class AddItemException extends RuntimeException {

    public AddItemException() {
        super();
    }

    public AddItemException(String message) {
        super(message);
    }

    public AddItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
