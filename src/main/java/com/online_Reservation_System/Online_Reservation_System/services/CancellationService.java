package com.online_Reservation_System.Online_Reservation_System.services;


import com.online_Reservation_System.Online_Reservation_System.model.Reservation;
import com.online_Reservation_System.Online_Reservation_System.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CancellationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Optional<Reservation> getReservation(String pnr) {
        return reservationRepository.findByPnr(pnr);
    }

    public String cancelReservation(String pnr) {
        Optional<Reservation> reservation = reservationRepository.findByPnr(pnr);
        if (reservation.isPresent()) {
            reservationRepository.delete(reservation.get());
            return "Reservation with PNR " + pnr + " canceled successfully";
        }
        return "Reservation not found";
    }
}
