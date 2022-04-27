package com.unitechApi.exception.ExceptionService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ImageException extends RuntimeException {
    public ImageException() {
        super();
    }

    public ImageException(String msg) {
        super(msg);
    }

    public ImageException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
