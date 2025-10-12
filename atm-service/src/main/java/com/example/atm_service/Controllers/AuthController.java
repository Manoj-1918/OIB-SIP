package com.example.atm_service.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.atm_service.dto.AuthRequest;
import com.example.atm_service.dto.AuthResponse;
import com.example.atm_service.Services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) { this.authService = authService; }

@PostMapping("/login")
public ResponseEntity<?> login(@Validated @RequestBody AuthRequest req) {
    return authService.login(req.getUsername(), req.getPin())
            .<ResponseEntity<?>>map(token ->
                    ResponseEntity.ok(new AuthResponse(token, req.getUsername())))
            .orElseGet(() ->
                    ResponseEntity.status(401).body("Invalid credentials"));
}


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(name = "X-ATMTOKEN", required = false) String token) {
        if (token != null) authService.logout(token);
        return ResponseEntity.ok("Logged out");
    }
}
