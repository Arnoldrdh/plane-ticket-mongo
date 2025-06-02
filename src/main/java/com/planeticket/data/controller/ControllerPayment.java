package com.planeticket.data.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planeticket.data.dto.BookingDetailDTO;
import com.planeticket.data.dto.FlightSummaryDTO;
import com.planeticket.data.dto.PaymentResponseDTO;
import com.planeticket.data.dto.RequestPayment;
import com.planeticket.data.dto.UserSummaryDTO;
import com.planeticket.data.model.ModelBooking;
import com.planeticket.data.model.ModelFlight;
import com.planeticket.data.model.ModelPayment;
import com.planeticket.data.model.ModelUser;
import com.planeticket.data.repository.RepositoryBooking;
import com.planeticket.data.repository.RepositoryFlight;
import com.planeticket.data.repository.RepositoryPayment;
import com.planeticket.data.repository.RepositoryUser;

@RestController
@RequestMapping("/api/payment")
public class ControllerPayment {
    @Autowired
    private RepositoryBooking rpBooking;

    @Autowired
    private RepositoryPayment rpPayment;

    @Autowired
    private RepositoryUser rpUser;

    @Autowired
    private RepositoryFlight rpFlight;

    @PostMapping("/pay")
    public PaymentResponseDTO payBooking(@RequestBody RequestPayment reqPayment) {
        // Cari booking berdasarkan ID (String)
        ModelBooking booking = rpBooking.findById(reqPayment.getBookingId()).orElse(null);

        if (booking == null) {
            throw new RuntimeException("Booking tidak ditemukan");
        }

        if ("Paid".equalsIgnoreCase(booking.getPaymentStatus())) {
            throw new RuntimeException("Sudah dibayar");
        }

        // Buat payment baru
        ModelPayment payment = new ModelPayment();
        payment.setPaymentMethod(reqPayment.getPaymentMethod());
        payment.setAmount(reqPayment.getAmount());
        payment.setPaymentStatus("Paid");
        payment.setPaymentTime(LocalDateTime.now().toString());
        payment.setBookingId(booking.getId()); // pakai ID sebagai relasi

        rpPayment.save(payment);

        // Update status booking jadi "Paid"
        booking.setPaymentStatus("Paid");
        rpBooking.save(booking);

        // Ambil data user dan flight
        ModelUser user = rpUser.findById(booking.getUserId()).orElse(null);
        ModelFlight flight = rpFlight.findById(booking.getFlightId()).orElse(null);

        // Bangun response DTO
        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setPaymentTime(payment.getPaymentTime());
        response.setAmount(payment.getAmount());

        // Booking detail
        BookingDetailDTO bookingDTO = new BookingDetailDTO();
        bookingDTO.setSeatNumber(booking.getSeatNumber());
        bookingDTO.setStatus(booking.getStatus());
        bookingDTO.setBookingTime(booking.getBookingTime());

        if (user != null) {
            UserSummaryDTO userDTO = new UserSummaryDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhoneNumber(user.getEmail());
            bookingDTO.setUser(userDTO);
        }

        if (flight != null) {
            FlightSummaryDTO flightDTO = new FlightSummaryDTO();
            flightDTO.setFlightNumber(flight.getFlightNumber());
            flightDTO.setDeparture(flight.getDeparture());
            flightDTO.setDestination(flight.getDestination());
            bookingDTO.setFlight(flightDTO);
        }

        response.setBooking(bookingDTO);
        return response;
    }

    // DELETE PAYMENT
    @DeleteMapping("/delete/{paymentId}")
    public boolean deletePayment(@PathVariable String paymentId) {
        if (rpPayment.existsById(paymentId)) {
            rpPayment.deleteById(paymentId);
            return true;
        }
        return false;
    }

}
