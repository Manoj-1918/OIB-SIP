package com.example.atm_service.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.atm_service.model.Transaction;
import com.example.atm_service.model.User;
import com.example.atm_service.Repository.TransactionRepository;
import com.example.atm_service.Repository.UserRepository;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final SessionService sessionService;

    public AccountService(UserRepository userRepository,
                          TransactionRepository transactionRepository,
                          SessionService sessionService) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.sessionService = sessionService;
    }

    public Optional<User> getUserByToken(String token) {
        return sessionService.getUsernameForToken(token)
                .flatMap(userRepository::findByUsername);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public Transaction deposit(User user, double amount) {
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);

        Transaction tx = new Transaction();
        tx.setUser(user);
        tx.setType("DEPOSIT");
        tx.setAmount(amount);
        tx.setDescription("Deposit via ATM");
        transactionRepository.save(tx);

        return tx;
    }

    @Transactional
    public Transaction withdraw(User user, double amount) {
        if (user.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);

        Transaction tx = new Transaction();
        tx.setUser(user);
        tx.setType("WITHDRAW");
        tx.setAmount(amount);
        tx.setDescription("Withdrawal via ATM");
        transactionRepository.save(tx);

        return tx;
    }

    @Transactional
    public Transaction transfer(User from, User to, double amount) {
        if (from.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance for transfer");
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        userRepository.save(from);
        userRepository.save(to);

        // Outgoing transaction
        Transaction out = new Transaction();
        out.setUser(from);
        out.setType("TRANSFER_OUT");
        out.setAmount(amount);
        out.setCounterparty(to.getUsername());
        out.setDescription("Transfer to " + to.getUsername());
        transactionRepository.save(out);

        // Incoming transaction
        Transaction in = new Transaction();
        in.setUser(to);
        in.setType("TRANSFER_IN");
        in.setAmount(amount);
        in.setCounterparty(from.getUsername());
        in.setDescription("Transfer from " + from.getUsername());
        transactionRepository.save(in);

        // Return the "out" transaction as confirmation to sender
        return out;
    }

    public List<Transaction> history(User user) {
        return transactionRepository.findByUserOrderByTimestampDesc(user);
    }
}
