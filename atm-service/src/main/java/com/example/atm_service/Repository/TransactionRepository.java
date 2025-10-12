package com.example.atm_service.Repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.atm_service.model.Transaction;
import com.example.atm_service.model.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserOrderByTimestampDesc(User user);
}
