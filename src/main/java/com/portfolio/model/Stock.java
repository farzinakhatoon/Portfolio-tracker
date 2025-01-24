package com.portfolio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @NotBlank(message = "Ticker cannot be blank.")
    private String ticker;

    @NotBlank(message = "Stock name cannot be blank.")
    private String name;

    @Min(value = 1, message = "Quantity must be at least 1.")
    private int quantity;

    @Min(value = 0, message = "Buy price cannot be negative.")
    private double buyPrice;

    private double currentPrice;

    // Getters and Setters
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
