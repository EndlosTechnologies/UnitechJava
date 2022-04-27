package com.unitechApi.exception.ExceptionService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductCategoryNotFound extends RuntimeException {
    public ProductCategoryNotFound() {
    }

    public ProductCategoryNotFound(String message) {
        super(message);
    }

    public ProductCategoryNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
