package com.online_Reservation_System.Online_Reservation_System.controller;

import com.online_Reservation_System.Online_Reservation_System.model.Reservation;

import com.online_Reservation_System.Online_Reservation_System.repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cancel")
@CrossOrigin(origins = "*")
public class CancellationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/{pnr}")
    public Optional<Reservation> getReservation(@PathVariable String pnr) {
        return reservationRepository.findByPnr(pnr);
    }

    @DeleteMapping("/{pnr}")
    public String cancelReservation(@PathVariable String pnr) {
        Optional<Reservation> reservation = reservationRepository.findByPnr(pnr);
        if (reservation.isPresent()) {
            reservationRepository.delete(reservation.get());
            return "Reservation with PNR " + pnr + " canceled successfully";
        } else {
            return "Reservation not found";
        }
    }
}