package com.unitechApi.exception.ExceptionService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnitNotFound extends RuntimeException{
    public UnitNotFound() {
    }

    public UnitNotFound(String message) {
        super(message);
    }

    public UnitNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
