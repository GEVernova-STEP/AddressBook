package com.example.addressbook.exception;

// Custom exception for "resource not found" cases
public class ResourceNotFoundException extends RuntimeException {

    // Constructor accepts error message
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
