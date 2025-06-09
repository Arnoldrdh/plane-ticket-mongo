package com.planeticket.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planeticket.data.dto.UserSummaryDTO;
import com.planeticket.data.model.ModelUser;
import com.planeticket.data.repository.RepositoryUser;

@Service
public class ServiceUser {
    @Autowired
    private RepositoryUser rpUser;

    public boolean userRegister(ModelUser user) {
        ModelUser existingUser = rpUser.findByEmailAndUsername(user.getEmail(), user.getUsername());
        if (existingUser != null) {
            return false;
        }

        rpUser.save(user);
        return true;
    }

    // login
    public boolean loginUser(ModelUser user) {
        ModelUser userData = rpUser.findByEmailAndPassword(user.getEmail(), user.getPassword());
        return userData != null;
    }

    // profile user
    public UserSummaryDTO userProfile(String email) {
        try {
            ModelUser userProfile = rpUser.findByEmail(email);
            if (userProfile == null) {
                return null;
            }

            UserSummaryDTO dto = new UserSummaryDTO();
            dto.setUsername(userProfile.getUsername());
            dto.setEmail(userProfile.getEmail());
            dto.setPhoneNumber(userProfile.getPhoneNumber());

            return dto;

        } catch (Exception e) {
            System.out.println("Error nya: " + e);
            return null;
        }
    }

    // update profile
    public boolean updateProfileUser(String email, ModelUser userData) {
        ModelUser user = rpUser.findByEmail(email);
        if (user != null) {
            user.setUsername(userData.getUsername());
            user.setEmail(userData.getEmail());
            user.setPassword(userData.getPassword());
            user.setPhoneNumber(userData.getPhoneNumber());
            rpUser.save(user);
            return true;
        }
        return false;
    }

    // getall user
    public Iterable<ModelUser> getAllUser() {
        return rpUser.findAll();
    }

    // deleteuser
    public boolean deleteUser(String email) {
        ModelUser userData = rpUser.findByEmail(email);
        if (userData != null) {
            rpUser.delete(userData);
            return true;
        }

        return false;
    }
}
