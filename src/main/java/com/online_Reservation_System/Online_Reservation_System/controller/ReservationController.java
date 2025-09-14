package com.online_Reservation_System.Online_Reservation_System.controller;

import com.online_Reservation_System.Online_Reservation_System.repository.TrainRepository;
import com.online_Reservation_System.Online_Reservation_System.repository.UserRepository;
import com.online_Reservation_System.Online_Reservation_System.repository.ReservationRepository;
import com.online_Reservation_System.Online_Reservation_System.dto.ReservationRequest;
import com.online_Reservation_System.Online_Reservation_System.dto.ReservationResponse;
import com.online_Reservation_System.Online_Reservation_System.model.Reservation;
import com.online_Reservation_System.Online_Reservation_System.model.Trains;
import com.online_Reservation_System.Online_Reservation_System.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private TrainRepository trainRepository;

        @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/trains")
    public List<Trains> getAllTrains() {
        return trainRepository.findAll();
    }

    @GetMapping("/search")
    public List<Trains> searchTrains(@RequestParam String source, @RequestParam String destination) {
        return trainRepository.findBySourceAndDestination(source, destination);
    }

   @PostMapping("/book")
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequest reservationRequest) {
        // Fetch user and train from the database
            System.out.println("Incoming request: " + reservationRequest);

            System.out.println();
            
        User user = userRepository.findByUsername(reservationRequest.getUsername());
        Trains train = trainRepository.findById(reservationRequest.getTrainNumber()).orElse(null);

        // Check if user or train exists
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found.");
        }
        if (train == null) {
            return ResponseEntity.badRequest().body("Train not found.");
        }

        // Check for available seats
        if (train.getAvailableSeats() <= 0) {
            return ResponseEntity.badRequest().body("No available seats on this train.");
        }

        // Create new reservation object
        Reservation reservation = new Reservation(
            user,
            train,
            reservationRequest.getClassType(),
            reservationRequest.getFromPlace(),
            reservationRequest.getToPlace(),
            reservationRequest.getJourneyDate()
        );

        // Generate PNR
        String pnr = generatePNR();
        reservation.setPnr(pnr);

        // Update available seats on the train
        train.setAvailableSeats(train.getAvailableSeats() - 1);

        try {
            // Save the reservation and update the train
            reservationRepository.save(reservation);
            trainRepository.save(train);
            return ResponseEntity.ok("Reservation successful! Your PNR is: " + pnr);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating reservation: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

@GetMapping("/my-reservation/{username}")
public List<ReservationResponse> getUserReservations(@PathVariable String username) {
    List<Reservation> reservations = reservationRepository.findByUserUsername(username);

    return reservations.stream().map(res -> {
        ReservationResponse dto = new ReservationResponse();
        dto.setPnr(res.getPnr());
        dto.setFromPlace(res.getFromPlace());
        dto.setToPlace(res.getToPlace());
        dto.setJourneyDate(res.getJourneyDate());
        dto.setTrainNumber(res.getTrain().getTrainNumber());
        dto.setTrainName(res.getTrain().getTrainName());
        dto.setClassType(res.getClassType());
        return dto;
    }).toList();
}


    // Helper function to generate a random PNR
    private String generatePNR() {
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder pnr = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            pnr.append(characters.charAt(random.nextInt(characters.length())));
        }
        return pnr.toString();
    }
}