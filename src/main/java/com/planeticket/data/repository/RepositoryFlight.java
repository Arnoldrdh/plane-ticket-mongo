package com.planeticket.data.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.planeticket.data.model.ModelFlight;

public interface RepositoryFlight extends MongoRepository<ModelFlight, String> {
    // cari penerbangan berdasarkan asal dan tujuan
    List<ModelFlight> findByDepartureAndDestination(String departure, String destination);

    // cari penerbangan berdarkan tanggal
    List<ModelFlight> findBydepartureTime(String departureTime);

}
