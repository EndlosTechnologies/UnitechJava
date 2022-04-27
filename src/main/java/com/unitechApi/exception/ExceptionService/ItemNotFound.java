package com.unitechApi.exception.ExceptionService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFound extends RuntimeException {
    public ItemNotFound() {
    }

    public ItemNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemNotFound(String message) {
        super(message);
    }
}
