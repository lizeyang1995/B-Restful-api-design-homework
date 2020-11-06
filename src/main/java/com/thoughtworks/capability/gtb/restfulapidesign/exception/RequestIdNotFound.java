package com.thoughtworks.capability.gtb.restfulapidesign.exception;

public class RequestIdNotFound extends RuntimeException {
    private String errorMessage;

    public RequestIdNotFound(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
