package com.planeticket.data.dto;

public class BookingResponseDTO {
    private String seatNumber;
    private String status;
    private String bookingTime;
    private String paymentStatus;
    private Double price;
    private UserSummaryDTO user;
    private FlightSummaryDTO flight;

        public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public String getSeatNumber() {
        return this.seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookingTime() {
        return this.bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public UserSummaryDTO getUser() {
        return this.user;
    }

    public void setUser(UserSummaryDTO user) {
        this.user = user;
    }

    public FlightSummaryDTO getFlight() {
        return this.flight;
    }

    public void setFlight(FlightSummaryDTO flight) {
        this.flight = flight;
    }

}
