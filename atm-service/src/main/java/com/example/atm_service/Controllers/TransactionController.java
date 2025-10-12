package com.example.atm_service.Controllers;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.atm_service.model.Transaction;
import com.example.atm_service.Services.AccountService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final AccountService accountService;

    public TransactionController(AccountService accountService) { this.accountService = accountService; }

    @GetMapping("/history")
    public ResponseEntity<?> history(@RequestHeader(name = "X-ATMTOKEN") String token) {
        var userOpt = accountService.getUserByToken(token);
        if (userOpt.isEmpty()) return ResponseEntity.status(401).body("Invalid token");
        List<Transaction> list = accountService.history(userOpt.get());
        // convert to lightweight DTOs (here we just return transactions)
        return ResponseEntity.ok(list);
    }
}
