package com.unitechApi.exception.ExceptionService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoleNotFound extends RuntimeException{
    public RoleNotFound() {
        super();
    }

    public RoleNotFound(String msg) {
        super(msg);
    }

    public RoleNotFound(String msg, Throwable cause) {
        super(msg, cause);
    }
}
