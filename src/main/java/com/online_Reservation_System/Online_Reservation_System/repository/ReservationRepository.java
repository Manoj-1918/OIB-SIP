package com.online_Reservation_System.Online_Reservation_System.repository;


import com.online_Reservation_System.Online_Reservation_System.model.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// public interface ReservationRepository extends JpaRepository<Reservation, Long> {
//     Optional<Reservation> findByPnr(String pnr);
// }
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByPnr(String pnr);

    List<Reservation> findByUserUsername(String username);

    List<Reservation> findByTrainTrainNumber(String trainNumber);
}
