package com.online_Reservation_System.Online_Reservation_System.model;



import java.time.LocalTime;

import jakarta.persistence.*;

@Entity
@Table(name = "trains")
public class Trains {

    @Id
    private String trainNumber;

    @Column(nullable = false)
    private String trainName;

    @Column(nullable = false)
    private String classType; // Sleeper, AC, etc.

    private String source;
    private String destination;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int availableSeats;


    public Trains() {}

    public Trains(String trainNumber, String trainName, String classType) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
    }

    // Getters & Setters
    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }

    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }

    public String getClassType() { return classType; }
    public void setClassType(String classType) { this.classType = classType; }

    // Getters & Setters for new fields

public String getSource() {
    return source;
}

public void setSource(String source) {
    this.source = source;
}

public String getDestination() {
    return destination;
}

public void setDestination(String destination) {
    this.destination = destination;
}

public LocalTime getDepartureTime() {
    return departureTime;
}

public void setDepartureTime(LocalTime departureTime) {
    this.departureTime = departureTime;
}

public LocalTime getArrivalTime() {
    return arrivalTime;
}

public void setArrivalTime(LocalTime arrivalTime) {
    this.arrivalTime = arrivalTime;
}

public int getAvailableSeats() {
    return availableSeats;
}

public void setAvailableSeats(int availableSeats) {
    this.availableSeats = availableSeats;
}

    // Helper function to display full train details
    public String getTrainInfo() {
        return trainNumber + " - " + trainName + " (" + classType + ")";
    }
}