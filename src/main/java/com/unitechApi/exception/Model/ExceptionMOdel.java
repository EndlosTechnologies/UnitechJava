package com.unitechApi.exception.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExceptionMOdel {
    private String title;
    private int status;
    private String details;
    private String timestamp;
    private String developerMessage;
    private Map<String, List<VadationErrorMessage>> validationErrors = new HashMap<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public Map<String, List<VadationErrorMessage>> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, List<VadationErrorMessage>> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
