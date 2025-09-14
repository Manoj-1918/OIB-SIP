package com.online_Reservation_System.Online_Reservation_System.services;

import com.online_Reservation_System.Online_Reservation_System.model.Reservation;
import com.online_Reservation_System.Online_Reservation_System.model.Trains;
import com.online_Reservation_System.Online_Reservation_System.repository.ReservationRepository;
import com.online_Reservation_System.Online_Reservation_System.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationService {

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Trains> getAllTrains() {
        return trainRepository.findAll();
    }

    public Reservation bookReservation(Reservation reservation) {
        reservation.setPnr(UUID.randomUUID().toString().substring(0, 8));
        reservation.setJourneyDate(LocalDate.parse(reservation.getJourneyDate().toString()));
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationByPnr(String pnr) {
        return reservationRepository.findByPnr(pnr);
    }

    public boolean cancelReservation(String pnr) {
        Optional<Reservation> reservation = reservationRepository.findByPnr(pnr);
        if (reservation.isPresent()) {
            reservationRepository.delete(reservation.get());
            return true;
        }
        return false;
    }
}
