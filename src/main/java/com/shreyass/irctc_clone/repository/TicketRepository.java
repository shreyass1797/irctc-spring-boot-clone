package com.shreyass.irctc_clone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shreyass.irctc_clone.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    // Spring magically writes: SELECT * FROM tickets WHERE user_id = ?
    List<Ticket> findByUser_Id(Long userId);
}