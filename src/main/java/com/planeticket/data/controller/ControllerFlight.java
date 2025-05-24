package com.planeticket.data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planeticket.data.model.ModelFlight;
import com.planeticket.data.repository.RepositoryFlight;

@RestController
@RequestMapping("/api/flight")
public class ControllerFlight {
    @Autowired
    private RepositoryFlight rpFlight;

    // add penerbangan
    @PostMapping("/add")
    public boolean addFlight(@RequestBody List<ModelFlight> flights) {
        rpFlight.saveAll(flights);
        return true;
    }

    // Daftar semua penerbangan
    @GetMapping("/all")
    public List<ModelFlight> getAllFlights() {
        return rpFlight.findAll();
    }

    // update penerbangan(soon)
    // delete penerbangan(soon)

    // Cari berdasarkan kota asal dan tujuan
    // cari penerbangan berdasrkan kota asal dan tujuan
    @PostMapping("/city")
    public Iterable<ModelFlight> flightByCity(@RequestBody ModelFlight flight) {
        return rpFlight.findByDepartureAndDestination(flight.getDeparture(), flight.getDestination());
    }
}
