package com.unitechApi.exception.Model;

public class VadationErrorMessage {
    private String code;
    private String message;

    public VadationErrorMessage(String field, String defaultMessage) {
        this.code=field;
        this.message=defaultMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
