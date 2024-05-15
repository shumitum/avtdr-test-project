package com.avtdr.vehicletracks.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    public record ErrorResponse(String message, String status, String path) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTimeValidationException(final TimeValidationException exc, HttpServletRequest request) {
        log.warn("Получен статус 400 BAD_REQUEST {}", exc.getMessage());
        return new ErrorResponse(exc.getMessage(), HttpStatus.BAD_REQUEST.toString(), request.getRequestURI());
    }
}