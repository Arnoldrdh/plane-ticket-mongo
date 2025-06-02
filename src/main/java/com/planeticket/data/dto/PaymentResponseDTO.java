package com.planeticket.data.dto;

public class PaymentResponseDTO {
    private String paymentMethod;
    private String paymentStatus;
    private String paymentTime;
    private double amount;
    private BookingDetailDTO booking;

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentTime() {
        return this.paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BookingDetailDTO getBooking() {
        return this.booking;
    }

    public void setBooking(BookingDetailDTO booking) {
        this.booking = booking;
    }

}
