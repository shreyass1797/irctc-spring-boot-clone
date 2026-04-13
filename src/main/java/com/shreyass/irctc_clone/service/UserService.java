package com.shreyass.irctc_clone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shreyass.irctc_clone.model.User;
import com.shreyass.irctc_clone.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Bring in the BCrypt machine we just built!
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        // Scramble the password before saving!
        String scrambledPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(scrambledPassword);
        
        return userRepository.save(user);
    }
}