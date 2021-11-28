package com.example.vismabooklibrary.exceptions;


import com.example.vismabooklibrary.dto.response.ErrorResponse;
import com.example.vismabooklibrary.enums.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(ResourceNotFoundException e) {
        log.warn("Resource not found: isbn = {}", e.getIsbn());

        String message = String.format("Resource with isbn = %s was not found", e.getIsbn());
        ErrorResponse errorResponse = new ErrorResponse(message, Error.RESOURCE_NOT_FOUND, 404);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
