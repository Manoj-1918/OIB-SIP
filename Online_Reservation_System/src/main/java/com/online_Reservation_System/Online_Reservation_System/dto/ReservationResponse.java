package com.online_Reservation_System.Online_Reservation_System.dto;

import java.time.LocalDate;

public class ReservationResponse {
    private String pnr;
    private String fromPlace;
    private String toPlace;
    private LocalDate journeyDate;
    private String trainNumber;
    private String trainName;
    private String classType;

    // Getters and Setters
    public String getPnr() {
        return pnr;
    }
    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
    public String getFromPlace() {
        return fromPlace;
    }
    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }
    public String getToPlace() {
        return toPlace;
    }
    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }
    public LocalDate getJourneyDate() {
        return journeyDate;
    }
    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }
    public String getTrainNumber() {
        return trainNumber;
    }
    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }
    public String getTrainName() {
        return trainName;
    }
    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }
    public String getClassType() {
        return classType;
    }
    public void setClassType(String classType) {
        this.classType = classType;
    }
}

