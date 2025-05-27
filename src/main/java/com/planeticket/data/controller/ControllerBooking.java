package com.planeticket.data.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planeticket.data.dto.RequestBooking;
import com.planeticket.data.model.ModelBooking;
import com.planeticket.data.model.ModelFlight;
import com.planeticket.data.model.ModelUser;
import com.planeticket.data.repository.RepositoryBooking;
import com.planeticket.data.repository.RepositoryFlight;
import com.planeticket.data.repository.RepositoryUser;

@RestController
@RequestMapping("/api/booking")
public class ControllerBooking {
    @Autowired
    private RepositoryBooking rpBooking;

    @Autowired
    private RepositoryFlight rpFlight;

    @Autowired
    private RepositoryUser rpUser;

    @PostMapping("/create")
    public ModelBooking createBooking(@RequestBody RequestBooking reqBooking) {
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

            return rpBooking.save(booking);

        } catch (Exception e) {
            System.out.println("error msg: " + e);
        }

        return null;
    }

    // update jadi cancel
    @PutMapping("/cancel/{bookingId}")
    public boolean cancelBooking(@PathVariable String bookingId) {
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

    // 🔍 Detail tiket berdasarkan ID booking
    @GetMapping("/detail/{bookingId}")
    public ModelBooking detailBooking(@PathVariable String bookingId) {
        return rpBooking.findById(bookingId).orElse(null);
    }

    // 📜 History pemesanan berdasarkan userId
    @GetMapping("/history/user/{userId}")
    public List<ModelBooking> historyBookingUser(@PathVariable String userId) {
        return rpBooking.findByUserId(userId);
    }

    // Hapus booking (pastikan payment sudah dihapus dulu)
    @DeleteMapping("/delete/{bookingId}")
    public boolean deleteBookingHistory(@PathVariable String bookingId) {
        if (rpBooking.existsById(bookingId)) {
            rpBooking.deleteById(bookingId);
            return true;
        }
        return false;
    }

}
