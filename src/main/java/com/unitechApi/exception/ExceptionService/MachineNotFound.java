package com.unitechApi.exception.ExceptionService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MachineNotFound  extends RuntimeException{
    public MachineNotFound() {
        super();
    }

    public MachineNotFound(String message) {
        super(message);
    }

    public MachineNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
