package com.shreyass.irctc_clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shreyass.irctc_clone.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // We will need a custom query to find a user by their username later for login.
    // Spring Boot writes the SQL just by reading the name of this method!
    User findByUsername(String username);
}
