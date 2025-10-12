package com.example.atm_service.Services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.atm_service.model.User;
import com.example.atm_service.Repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final SessionService sessionService;

    public AuthService(UserRepository userRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
    }

    @Transactional
    public Optional<String> login(String username, String pin) {
        Optional<User> uOpt = userRepository.findByUsername(username);
        if (uOpt.isPresent()) {
            User u = uOpt.get();
            if (u.getPin().equals(pin)) { // simple check. In production hash compare.
                String token = sessionService.createTokenForUser(u);
                return Optional.of(token);
            }
        }
        return Optional.empty();
    }

    public void logout(String token) {
        sessionService.invalidate(token);
    }
}
