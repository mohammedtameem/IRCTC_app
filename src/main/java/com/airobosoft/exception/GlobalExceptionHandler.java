package com.airobosoft.exception;

import com.airobosoft.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException exception) {

         ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Train not Found" + exception.getMessage())
                .code("NOT_FOUND")
                .success(false)
                .localDateTime(LocalDateTime.now())
                .build();

         return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotAllowed(MethodArgumentNotValidException exception) {

        Map<String, String> errorResponse = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {

                    errorResponse.put(
                            error.getField(),
                            error.getDefaultMessage()
                    );

                });

        return ResponseEntity.badRequest().body(errorResponse);

    }
}
