package com.planeticket.data.controller;

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

import com.planeticket.data.dto.BookingResponseDTO;
import com.planeticket.data.dto.RequestBooking;
import com.planeticket.data.model.ModelBooking;
import com.planeticket.data.repository.RepositoryBooking;
import com.planeticket.data.service.ServiceBooking;

@RestController
@RequestMapping("/api/booking")
public class ControllerBooking {
    @Autowired
    private ServiceBooking srBooking;

    @Autowired
    private RepositoryBooking rpBooking;

    @PostMapping("/create")
    public BookingResponseDTO createBooking(@RequestBody RequestBooking reqBooking) {

        return srBooking.createBooking(reqBooking);
    }

    // update jadi cancel
    @PutMapping("/cancel/{bookingId}")
    public boolean cancelBooking(@PathVariable String bookingId) {

        return srBooking.cancelBooking(bookingId);

    }

    // Detail tiket berdasarkan ID booking
    @GetMapping("/detail/{bookingId}")
    public ModelBooking detailBooking(@PathVariable String bookingId) {
        return srBooking.detailBooking(bookingId);
    }

    // History pemesanan berdasarkan userId
    @GetMapping("/history/user/{userId}")
    public List<ModelBooking> historyBookingUser(@PathVariable String userId) {
        return rpBooking.findByUserId(userId);
    }

    // Hapus booking (pastikan payment sudah dihapus dulu)
    @DeleteMapping("/delete/{bookingId}")
    public boolean deleteBookingHistory(@PathVariable String bookingId) {
        return srBooking.deleteBookingHistory(bookingId);
    }

}
