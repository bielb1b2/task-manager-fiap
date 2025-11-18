package com.example.task_manager.exceptions.core;

import com.example.task_manager.exceptions.NotFoundException;
import com.example.task_manager.exceptions.ValidateException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalErrorHandling {

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<ErrorResponse> handleValidateException(
            ValidateException exception,
            HttpServletRequest request
    ) {

        ErrorResponse body = new ErrorResponse(
                exception.getMessage(),
                request.getRequestURI(),
                Instant.now().toString()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(
            NotFoundException exception,
            HttpServletRequest request
    ) {

        ErrorResponse body = new ErrorResponse(
                exception.getMessage(),
                request.getRequestURI(),
                Instant.now().toString()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

}
