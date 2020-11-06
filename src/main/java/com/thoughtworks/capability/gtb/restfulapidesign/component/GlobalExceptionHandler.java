package com.thoughtworks.capability.gtb.restfulapidesign.component;

import com.thoughtworks.capability.gtb.restfulapidesign.exception.Error;
import com.thoughtworks.capability.gtb.restfulapidesign.exception.RequestIdNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RequestIdNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error studentExceptionHandler(RequestIdNotFound exception) {
        Error error = new Error();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        return error;
    }
}
