package com.planeticket.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.planeticket.data.model.ModelBooking;

public interface RepositoryBooking extends MongoRepository<ModelBooking, String> {

}
