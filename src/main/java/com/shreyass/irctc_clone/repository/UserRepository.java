package com.shreyass.irctc_clone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shreyass.irctc_clone.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // ADD THIS LINE
    Optional<User> findByUsername(String username);
}