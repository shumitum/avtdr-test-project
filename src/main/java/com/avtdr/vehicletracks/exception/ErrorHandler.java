package com.avtdr.vehicletracks.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    public record ErrorResponse(@Schema(description = "Error message", example = "must not be blank. Value: null")String message,
                                @Schema(description = "Error status", example = "400 BAD_REQUEST") String status,
                                @Schema(example = "user/1/tasks") String path) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTimeValidationException(final TimeValidationException exc, HttpServletRequest request) {
        log.warn("Получен статус 400 BAD_REQUEST {}", exc.getMessage());
        return new ErrorResponse(exc.getMessage(), HttpStatus.BAD_REQUEST.toString(), request.getRequestURI());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchElementException(final NoSuchElementException exc, HttpServletRequest request) {
        log.warn("Получен статус 404 NOT FOUND {}", exc.getMessage());
        return new ErrorResponse(exc.getMessage(), HttpStatus.NOT_FOUND.toString(), request.getRequestURI());
    }
}