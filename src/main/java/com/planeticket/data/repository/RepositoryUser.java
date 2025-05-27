package com.planeticket.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.planeticket.data.model.ModelUser;

public interface RepositoryUser extends MongoRepository<ModelUser, String> {
    ModelUser findByEmailAndPassword(String email, String password);

    ModelUser findByEmailAndUsername(String email, String username);

    ModelUser findByEmail(String email);

    boolean existsByUsernameAndEmail(String username, String email);
}
