package com.planeticket.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planeticket.data.model.ModelFlight;
import com.planeticket.data.repository.RepositoryFlight;

@Service
public class ServiceFlight {
    @Autowired
    private RepositoryFlight rpFlight;

    // add penerbangan
    public boolean addFlight(List<ModelFlight> flights) {
        rpFlight.saveAll(flights);
        return true;
    }

    // Daftar semua penerbangan
    public List<ModelFlight> getAllFlights() {
        return rpFlight.findAll();
    }

    // cari penerbangan berdasrkan kota asal dan tujuan
    public Iterable<ModelFlight> flightByCity(ModelFlight flight) {
        return rpFlight.findByDepartureAndDestination(flight.getDeparture(), flight.getDestination());
    }
}
