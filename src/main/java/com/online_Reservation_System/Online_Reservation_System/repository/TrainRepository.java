package com.online_Reservation_System.Online_Reservation_System.repository;



import com.online_Reservation_System.Online_Reservation_System.model.Trains;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
public interface TrainRepository extends JpaRepository<Trains, String> {
    // New function to find trains by source and destination
    List<Trains> findBySourceAndDestination(String source, String destination);
}
