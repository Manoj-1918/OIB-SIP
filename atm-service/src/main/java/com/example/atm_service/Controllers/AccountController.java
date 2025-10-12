package com.example.atm_service.Controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.atm_service.dto.AmountRequest;
import com.example.atm_service.dto.TransferRequest;
import com.example.atm_service.model.Transaction;
import com.example.atm_service.Services.AccountService;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) { this.accountService = accountService; }

    // get current balance
@GetMapping("/balance")
public ResponseEntity<?> balance(@RequestHeader(name = "X-ATMTOKEN") String token) {
    return accountService.getUserByToken(token)
            .<ResponseEntity<?>>map(user -> ResponseEntity.ok(user.getBalance()))
            .orElseGet(() -> ResponseEntity.status(401).body("Invalid session"));
}


    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody AmountRequest req) {
        var userOpt = accountService.getUserByToken(req.getToken());
        if (userOpt.isEmpty()) return ResponseEntity.status(401).body("Invalid token");
        if (req.getAmount() == null || req.getAmount() <= 0) return ResponseEntity.badRequest().body("Invalid amount");

        Transaction tx = accountService.deposit(userOpt.get(), req.getAmount());
        return ResponseEntity.ok(tx);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody AmountRequest req) {
        var userOpt = accountService.getUserByToken(req.getToken());
        if (userOpt.isEmpty()) return ResponseEntity.status(401).body("Invalid token");
        if (req.getAmount() == null || req.getAmount() <= 0) return ResponseEntity.badRequest().body("Invalid amount");

        // REMOVE try-catch BLOCK
        Transaction tx = accountService.withdraw(userOpt.get(), req.getAmount());
        return ResponseEntity.ok(tx);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest req) {
        var fromOpt = accountService.getUserByToken(req.getToken());
        if (fromOpt.isEmpty()) return ResponseEntity.status(401).body("Invalid token");
        if (req.getAmount() == null || req.getAmount() <= 0)
            return ResponseEntity.badRequest().body("Invalid amount");
        if (req.getToUsername() == null || req.getToUsername().trim().isEmpty())
            return ResponseEntity.badRequest().body("Invalid recipient");

        var toOpt = accountService.findByUsername(req.getToUsername());
        if (toOpt.isEmpty()) return ResponseEntity.badRequest().body("Recipient not found");

        // REMOVE try-catch BLOCK
        Transaction tx = accountService.transfer(fromOpt.get(), toOpt.get(), req.getAmount());
        return ResponseEntity.ok(tx);
    }
}
