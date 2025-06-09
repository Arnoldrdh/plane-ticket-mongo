package com.planeticket.data.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planeticket.data.dto.BookingResponseDTO;
import com.planeticket.data.dto.FlightSummaryDTO;
import com.planeticket.data.dto.RequestBooking;
import com.planeticket.data.dto.UserSummaryDTO;
import com.planeticket.data.model.ModelBooking;
import com.planeticket.data.model.ModelFlight;
import com.planeticket.data.model.ModelUser;
import com.planeticket.data.repository.RepositoryBooking;
import com.planeticket.data.repository.RepositoryFlight;
import com.planeticket.data.repository.RepositoryUser;

@Service
public class ServiceBooking {
    @Autowired
    private RepositoryBooking rpBooking;

    @Autowired
    private RepositoryFlight rpFlight;

    @Autowired
    private RepositoryUser rpUser;

    public BookingResponseDTO createBooking(RequestBooking reqBooking) {
        try {

            // ambil data penerbangan dan user
            ModelFlight flightData = rpFlight.findById(reqBooking.getFlightId()).orElse(null);
            ModelUser userData = rpUser.findById(reqBooking.getUserId()).orElse(null);

            // Validasi ketersediaan flight & user
            if (flightData == null || userData == null) {
                throw new RuntimeException("Flight or User not found");
            }

            // cek kursi ada atau tidal
            if (flightData.getAvailableSeats() <= 0) {
                throw new RuntimeException("kursi habis");

            }

            // Buat data booking baru
            ModelBooking booking = new ModelBooking();
            booking.setFlightId(flightData.getId());
            booking.setUserId(userData.getId());
            booking.setSeatNumber(reqBooking.getSeatNumber());
            booking.setStatus("Booked");
            booking.setPaymentStatus("Unpaid");
            booking.setBookingTime(LocalDateTime.now().toString());

            // Update seat di flight
            flightData.setAvailableSeats(flightData.getAvailableSeats() - 1);
            rpFlight.save(flightData);

            ModelBooking savedBooking = rpBooking.save(booking);

            // Build response DTO
            BookingResponseDTO response = new BookingResponseDTO();
            response.setSeatNumber(savedBooking.getSeatNumber());
            response.setStatus(savedBooking.getStatus());
            response.setPrice(flightData.getPrice());
            response.setBookingTime(savedBooking.getBookingTime());
            response.setPaymentStatus(savedBooking.getPaymentStatus());

            // Set user
            UserSummaryDTO userDTO = new UserSummaryDTO();
            userDTO.setUsername(userData.getUsername());
            userDTO.setEmail(userData.getEmail());
            userDTO.setPhoneNumber(userData.getPhoneNumber());
            response.setUser(userDTO);

            // Set flight
            FlightSummaryDTO flightDTO = new FlightSummaryDTO();
            flightDTO.setFlightNumber(flightData.getFlightNumber());
            flightDTO.setDeparture(flightData.getDeparture());
            flightDTO.setDestination(flightData.getDestination());
            flightDTO.setDepartureTime(flightData.getDepartureTime());
            flightDTO.setArrivalTime(flightData.getArrivalTime());
            response.setFlight(flightDTO);

            return response;

        } catch (Exception e) {
            System.out.println("error msg: " + e);
        }

        return null;
    }

    // update jadi cancel
    public boolean cancelBooking(String bookingId) {
        ModelBooking booking = rpBooking.findById(bookingId).orElse(null);
        if (booking == null) {
            return false;
        }

        if (!"Canceled".equalsIgnoreCase(booking.getStatus())) {
            booking.setStatus("Canceled");
            rpBooking.save(booking);

            ModelFlight flight = rpFlight.findById(booking.getFlightId()).orElse(null);
            if (flight != null) {
                flight.setAvailableSeats(flight.getAvailableSeats() + 1);
                rpFlight.save(flight);
            }

            return true;

        }

        return false;

    }

    // Detail tiket berdasarkan ID booking
    public ModelBooking detailBooking(String bookingId) {
        return rpBooking.findById(bookingId).orElse(null);
    }

    // History pemesanan berdasarkan userId
    public List<ModelBooking> historyBookingUser(String userId) {
        return rpBooking.findByUserId(userId);
    }

    // Hapus booking (pastikan payment sudah dihapus dulu)
    public boolean deleteBookingHistory(String bookingId) {
        if (rpBooking.existsById(bookingId)) {
            rpBooking.deleteById(bookingId);
            return true;
        }
        return false;
    }
}
