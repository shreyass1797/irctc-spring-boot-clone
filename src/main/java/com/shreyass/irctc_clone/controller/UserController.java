package com.shreyass.irctc_clone.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shreyass.irctc_clone.dto.UserResponse;
import com.shreyass.irctc_clone.model.User; // Don't forget to import!
import com.shreyass.irctc_clone.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUserProfile(Principal principal) {
        // 1. Find who is currently logged in (the "Principal" object contains the username from the JWT)
        String currentUsername = principal.getName(); 
        
        // 2. Fetch their full profile from PostgreSQL
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Map the raw entity to the safe DTO
        UserResponse responseDto = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );

        // 4. Return the safe DTO
        return ResponseEntity.ok(responseDto);
    }
}