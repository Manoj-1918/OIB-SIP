package com.example.atm_service.dto;

public class AmountRequest {
    private String token;
    private Double amount;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}
