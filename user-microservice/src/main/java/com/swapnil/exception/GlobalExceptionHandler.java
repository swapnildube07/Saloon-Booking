package com.swapnil.exception;

import com.swapnil.payload.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) // Handles all exceptions
    public ResponseEntity<ExceptionResponse> handleGlobalException(Exception ex, WebRequest request) {

        ExceptionResponse response = new ExceptionResponse(
                ex.getMessage(),          // Detailed error message
                request.getDescription(false), // Request details (URL)
                LocalDateTime.now()       // Timestamp of error
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
