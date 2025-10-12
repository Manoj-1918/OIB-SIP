package com.example.atm_service.dto;



public class TransferRequest {
    private String token;
    private String toUsername;
    private Double amount;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getToUsername() { return toUsername; }
    public void setToUsername(String toUsername) { this.toUsername = toUsername; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}
