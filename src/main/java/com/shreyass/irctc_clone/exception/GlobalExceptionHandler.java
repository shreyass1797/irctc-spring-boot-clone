package com.shreyass.irctc_clone.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// @ControllerAdvice tells Spring: "If any Controller anywhere throws an error, send it to me first!"
@ControllerAdvice
public class GlobalExceptionHandler {

    // This specifically catches the validation errors (those @NotBlank and @Size messages we put in our Train model)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        // This loops through all the rules the user broke and grabs our custom messages
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
            
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // A generic catch-all for any other runtime errors (like "Train not found!")
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeExceptions(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
