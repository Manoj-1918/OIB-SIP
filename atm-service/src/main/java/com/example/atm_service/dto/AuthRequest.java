package com.example.atm_service.dto;

public class AuthRequest {
    private String username;
    private String pin;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
}

