package com.planeticket.data.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.planeticket.data.model.ModelBooking;

public interface RepositoryBooking extends MongoRepository<ModelBooking, String> {
    List<ModelBooking> findByUserId(String userId);
}
