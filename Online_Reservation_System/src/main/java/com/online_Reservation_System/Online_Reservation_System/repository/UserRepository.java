package com.online_Reservation_System.Online_Reservation_System.repository;

import com.online_Reservation_System.Online_Reservation_System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}