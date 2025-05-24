package com.mdx.xyphose.usermgmt.entity.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class PaymentDTO {
    private String venueId;
    private LocalDate date;
    private LocalTime time;
    private int slot;
    private int duration;
    private String userEmail;
    private double amount;
    private String paymentMethod;
    private int seatsBooked;

    public PaymentDTO() {
    }

    public PaymentDTO(String venueId, int seatsBooked, String paymentMethod, double amount,
                      String userEmail, int slot, LocalTime time, LocalDate date, int duration) {
        this.venueId = venueId;
        this.seatsBooked = seatsBooked;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.userEmail = userEmail;
        this.slot = slot;
        this.time = time;
        this.date = date;
        this.duration = duration;
    }

    // Getters and Setters
    public String getVenueId() { return venueId; }
    public void setVenueId(String venueId) { this.venueId = venueId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }

    public int getSlot() { return slot; }
    public void setSlot(int slot) { this.slot = slot; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public int getSeatsBooked() { return seatsBooked; }
    public void setSeatsBooked(int seatsBooked) { this.seatsBooked = seatsBooked; }
}