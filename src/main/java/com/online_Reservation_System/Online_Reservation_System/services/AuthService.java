package com.online_Reservation_System.Online_Reservation_System.services;

import com.online_Reservation_System.Online_Reservation_System.model.User;

import com.online_Reservation_System.Online_Reservation_System.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        return userRepository.save(user);
    }

    public boolean validateCredentials(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}

