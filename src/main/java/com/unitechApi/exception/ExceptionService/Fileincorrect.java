package com.unitechApi.exception.ExceptionService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class Fileincorrect extends RuntimeException {
    public Fileincorrect() {
        super();
    }

    public Fileincorrect(String msg) {
        super(msg);
    }

    public Fileincorrect(String msg, Throwable cause) {
        super(msg, cause);
    }

}

