package com.example.atm_service.Controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.atm_service.dto.TransferRequest;
import com.example.atm_service.Services.AccountService;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {
    private final AccountService accountService;

    public TransferController(AccountService accountService) { this.accountService = accountService; }

    @PostMapping
    public ResponseEntity<?> transfer(@RequestBody TransferRequest req) {
        var fromOpt = accountService.getUserByToken(req.getToken());
        if (fromOpt.isEmpty()) return ResponseEntity.status(401).body("Invalid token");

        if (req.getToUsername() == null || req.getToUsername().isBlank()) return ResponseEntity.badRequest().body("Recipient required");
        if (req.getAmount() == null || req.getAmount() <= 0) return ResponseEntity.badRequest().body("Amount must be > 0");

        // find recipient by username
        var toOpt = accountService.findByUsername(req.getToUsername());
        if (toOpt.isEmpty()) return ResponseEntity.badRequest().body("Recipient not found");

        try {
            accountService.transfer(fromOpt.get(), toOpt.get(), req.getAmount());
            return ResponseEntity.ok("Transfer successful");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
