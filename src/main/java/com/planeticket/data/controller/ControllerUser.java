package com.planeticket.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planeticket.data.dto.UserSummaryDTO;
import com.planeticket.data.model.ModelUser;
import com.planeticket.data.repository.RepositoryUser;
import com.planeticket.data.service.ServiceUser;

@RestController
@RequestMapping("/api/user")
public class ControllerUser {
    @Autowired
    private ServiceUser srUser;

    @Autowired
    private RepositoryUser rpUser;

    // register
    @PostMapping("/register")
    public boolean userRegister(@RequestBody ModelUser user) {
        // ModelUser existingUser = rpUser.findByEmailAndUsername(user.getEmail(),
        // user.getUsername());
        // if (existingUser != null) {
        // return false;
        // }

        // rpUser.save(user);
        return srUser.userRegister(user);
    }

    // login
    @PostMapping("/login")
    public boolean loginUser(@RequestBody ModelUser user) {
        // ModelUser userData = rpUser.findByEmailAndPassword(user.getEmail(),
        // user.getPassword());
        // return userData != null;
        return srUser.loginUser(user);
    }

    // profile user
    @GetMapping("/profile/{email}")
    public UserSummaryDTO userProfile(@PathVariable String email) {
        // try {
        // ModelUser userProfile = rpUser.findByEmail(email);
        // if (userProfile == null) {
        // return null;
        // }

        // UserSummaryDTO dto = new UserSummaryDTO();
        // dto.setUsername(userProfile.getUsername());
        // dto.setEmail(userProfile.getEmail());
        // dto.setPhoneNumber(userProfile.getPhoneNumber());

        // return dto;

        // } catch (Exception e) {
        // System.out.println("Error nya: " + e);
        // return null;
        // }
        return srUser.userProfile(email);
    }

    // update profile
    @PutMapping("/update/{email}")
    public boolean updateProfileUser(@PathVariable String email, @RequestBody ModelUser userData) {
        // ModelUser user = rpUser.findByEmail(email);
        // if (user != null) {
        // user.setUsername(userData.getUsername());
        // user.setEmail(userData.getEmail());
        // user.setPassword(userData.getPassword());
        // user.setPhoneNumber(userData.getPhoneNumber());
        // rpUser.save(user);
        // return true;
        // }
        // return false;

        return srUser.updateProfileUser(email, userData);
    }

    // deleteuser
    @DeleteMapping("/delete/{email}")
    public boolean deleteUser(@PathVariable String email) {
        // ModelUser userData = rpUser.findByEmail(email);
        // if (userData != null) {
        // rpUser.delete(userData);
        // return true;
        // }

        // return false;

        return srUser.deleteUser(email);
    }
}
