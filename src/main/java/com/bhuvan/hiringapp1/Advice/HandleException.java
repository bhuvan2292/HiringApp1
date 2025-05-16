package com.bhuvan.hiringapp1.Advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleException extends Exception {
    @ExceptionHandler(HandleException.class)
    public String handleException(HandleException e) {
        return "Error: " + e.getMessage();
    }
}
