package com.example.addressbook.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // -------- VALIDATION ERRORS --------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach(err -> {
                    String field =
                            ((FieldError) err).getField();
                    String msg =
                            err.getDefaultMessage();
                    errors.put(field, msg);
                });

        log.warn("Validation failed: {}", errors);

        return ResponseEntity.badRequest().body(errors);
    }

    // -------- NOT FOUND --------
    @ExceptionHandler(AddressBookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            AddressBookNotFoundException ex) {

        log.error("Not found: {}", ex.getMessage());

        return ResponseEntity
                .status(404)
                .body(new ErrorResponse(
                        ex.getMessage(), 404));
    }

    // -------- BAD REQUEST --------
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            IllegalArgumentException ex) {

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(
                        ex.getMessage(), 400));
    }

    // -------- GENERIC --------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(
            Exception ex) {

        log.error("Unhandled error", ex);

        return ResponseEntity
                .status(500)
                .body(new ErrorResponse(
                        "Internal server error", 500));
    }
}
