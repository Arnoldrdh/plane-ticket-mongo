package com.planeticket.data.dto;

public class RequestBooking {
    public String seatNumber;
    public String flightId; // string, karena id MongoDB
    public String userId;

    public String getSeatNumber() {
        return this.seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getFlightId() {
        return this.flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
