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
import com.planeticket.data.service.ServiceUser;

@RestController
@RequestMapping("/api/user")
public class ControllerUser {
    @Autowired
    private ServiceUser srUser;

    // register
    @PostMapping("/register")
    public boolean userRegister(@RequestBody ModelUser user) {
        return srUser.userRegister(user);
    }

    // login
    @PostMapping("/login")
    public boolean loginUser(@RequestBody ModelUser user) {

        return srUser.loginUser(user);
    }

    // profile user
    @GetMapping("/profile/{email}")
    public UserSummaryDTO userProfile(@PathVariable String email) {
        return srUser.userProfile(email);
    }

    // update profile
    @PutMapping("/update/{email}")
    public boolean updateProfileUser(@PathVariable String email, @RequestBody ModelUser userData) {
        return srUser.updateProfileUser(email, userData);
    }

    // getall user
    @GetMapping("/getAll")
    public Iterable<ModelUser> getAllUser() {
        return srUser.getAllUser();
    }

    // deleteuser
    @DeleteMapping("/delete/{email}")
    public boolean deleteUser(@PathVariable String email) {
        return srUser.deleteUser(email);
    }
}
