package com.example.atm_service.Services;


import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.example.atm_service.model.User;

@Service
public class SessionService {
    // simple in-memory token store: token -> username
    private final Map<String, String> tokenToUsername = new ConcurrentHashMap<>();

    public String createTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        tokenToUsername.put(token, user.getUsername());
        return token;
    }

    public Optional<String> getUsernameForToken(String token) {
        if (token == null) return Optional.empty();
        return Optional.ofNullable(tokenToUsername.get(token));
    }

    public void invalidate(String token) {
        tokenToUsername.remove(token);
    }
}
