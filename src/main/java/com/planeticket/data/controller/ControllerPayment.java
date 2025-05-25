package com.planeticket.data.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planeticket.data.dto.RequestPayment;
import com.planeticket.data.model.ModelBooking;
import com.planeticket.data.model.ModelPayment;
import com.planeticket.data.repository.RepositoryBooking;
import com.planeticket.data.repository.RepositoryPayment;

@RestController
@RequestMapping("/api/payment")
public class ControllerPayment {
    @Autowired
    private RepositoryBooking rpBooking;

    @Autowired
    private RepositoryPayment rpPayment;

    @PostMapping("/pay")
    public ModelPayment payBooking(@RequestBody RequestPayment reqPayment) {
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

        return payment;
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
