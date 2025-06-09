package com.planeticket.data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planeticket.data.model.ModelFlight;
import com.planeticket.data.service.ServiceFlight;

@RestController
@RequestMapping("/api/flight")
public class ControllerFlight {
    @Autowired
    private ServiceFlight srFlight;

    // add penerbangan
    @PostMapping("/add")
    public boolean addFlight(@RequestBody List<ModelFlight> flights) {
        return srFlight.addFlight(flights);
    }

    // Daftar semua penerbangan
    @GetMapping("/all")
    public List<ModelFlight> getAllFlights() {
        return srFlight.getAllFlights();
    }

    // cari penerbangan berdasrkan kota asal dan tujuan
    @PostMapping("/city")
    public Iterable<ModelFlight> flightByCity(@RequestBody ModelFlight flight) {
        return srFlight.flightByCity(flight);
    }
}
