package com.unitechApi.exception.ExceptionService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class PasswordIncorrect extends RuntimeException{
    public PasswordIncorrect() {
    }

    public PasswordIncorrect(String message) {
        super(message);
    }

    public PasswordIncorrect(String message, Throwable cause) {
        super(message, cause);
    }
}
