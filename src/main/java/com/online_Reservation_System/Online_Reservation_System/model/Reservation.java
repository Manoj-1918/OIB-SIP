package com.online_Reservation_System.Online_Reservation_System.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String classType;
    private String fromPlace;
    private String toPlace;
    private LocalDate journeyDate;

    @Column(unique = true)
    private String pnr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", nullable = false)
    private Trains train;

    public Reservation() {}

    public Reservation(User user, Trains train, String classType,
                       String fromPlace, String toPlace, LocalDate journeyDate) {
        this.user = user;
        this.train = train;
        this.classType = classType;
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        this.journeyDate = journeyDate;
    }

    // ---------- Getters & Setters ----------
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getClassType() {
        return classType;
    }
    public void setClassType(String classType) {
        this.classType = classType;
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

    public String getPnr() {
        return pnr;
    }
    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Trains getTrain() {
        return train;
    }
    public void setTrain(Trains train) {
        this.train = train;
    }

    // Utility: short summary
    public String summary() {
        return "PNR: " + pnr + " | " + fromPlace + " → " + toPlace +
               " on " + journeyDate + " | Train: " + train.getTrainNumber();
    }
}
