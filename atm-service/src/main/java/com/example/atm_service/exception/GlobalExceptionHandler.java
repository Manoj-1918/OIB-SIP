package com.example.atm_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.Map; // Add this import

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleBadArgs(IllegalArgumentException ex) {
        // Change: Return a Map/JSON object instead of a plain string
        return ResponseEntity.badRequest().body(
            Map.of("message", ex.getMessage()) // This ensures the body is {"message": "Insufficient balance"}
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOther(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(500).body(
            Map.of("message", "Internal server error: " + ex.getMessage())
        );
    }
}