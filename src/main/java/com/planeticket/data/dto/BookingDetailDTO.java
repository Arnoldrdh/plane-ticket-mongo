package com.planeticket.data.dto;

public class BookingDetailDTO {
    private String seatNumber;
    private String status;
    private String bookingTime;


    private UserSummaryDTO user;
    private FlightSummaryDTO flight;

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
