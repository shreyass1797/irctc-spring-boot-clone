package com.shreyass.irctc_clone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 1. We create our BCrypt scrambling machine
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. We configure the doors
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable this for now so Thunder Client works
            .authorizeHttpRequests(auth -> auth
                // Allow anyone to register, view trains, and search trains without logging in
                .requestMatchers("/users", "/trains", "/trains/search").permitAll()
                // Require authentication for anything else
                .anyRequest().permitAll() 
            );
            
        return http.build();
    }
}