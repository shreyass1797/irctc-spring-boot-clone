package com.shreyass.irctc_clone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shreyass.irctc_clone.dto.AuthRequest;
import com.shreyass.irctc_clone.dto.AuthResponse;
import com.shreyass.irctc_clone.model.User;
import com.shreyass.irctc_clone.repository.UserRepository;
import com.shreyass.irctc_clone.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // --- 1. REGISTER ENDPOINT ---
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword())); // Hash the password!
        newUser.setRole("USER");
        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully");
    }

    // --- 2. LOGIN ENDPOINT ---
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest request) {
        // 1. Verify the username and password match the database
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 2. If successful, find the user to get their role
        User user = userRepository.findByUsername(request.getUsername()).get();

        // 3. Generate the Wristband (JWT)
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        // 4. Return it to the client
        return ResponseEntity.ok(new AuthResponse(token));
    }

}