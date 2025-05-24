package com.planeticket.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.planeticket.data.model.ModelPayment;

public interface RepositoryPayment extends MongoRepository<ModelPayment, String> {

}
