package com.unitechApi.exception.ExceptionService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class DateMisMatchException extends RuntimeException{
    public DateMisMatchException() {
        super();
    }

    public DateMisMatchException(String msg) {
        super(msg);
    }

    public DateMisMatchException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
