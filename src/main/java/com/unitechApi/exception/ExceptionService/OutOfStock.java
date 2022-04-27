package com.unitechApi.exception.ExceptionService;

import com.unitechApi.store.issue.model.IssueStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OutOfStock extends RuntimeException{
    public OutOfStock() {
    }

    public OutOfStock(String message) {
        super(message);
    }

    public OutOfStock(String message, Throwable cause) {
        super(message, cause);
    }
}
