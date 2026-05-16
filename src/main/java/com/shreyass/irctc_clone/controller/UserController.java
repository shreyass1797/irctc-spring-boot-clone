package com.shreyass.irctc_clone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shreyass.irctc_clone.model.User;
import com.shreyass.irctc_clone.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    
    @Autowired
    private UserService userService;

    @PostMapping
    public User register(@Valid @RequestBody User user) {
        return userService.registerUser(user);
    }
}