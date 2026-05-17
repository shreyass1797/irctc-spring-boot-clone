package com.shreyass.irctc_clone.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; // Changed!
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shreyass.irctc_clone.dto.BookingRequest;
import com.shreyass.irctc_clone.dto.TicketResponse;
import com.shreyass.irctc_clone.model.Ticket;
import com.shreyass.irctc_clone.model.User;
import com.shreyass.irctc_clone.repository.UserRepository;
import com.shreyass.irctc_clone.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserRepository userRepository;

    // --- 1. SECURE BOOKING WITH DTO ---
    @PostMapping("/book")
    public ResponseEntity<TicketResponse> bookTrainTicket(
            Principal principal, 
            @RequestBody BookingRequest request) { // Client now sends JSON!
        
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Book the ticket
        Ticket rawTicket = ticketService.bookTicket(user.getId(), request.getTrainId(), request.getSeatClass());

        // Convert and return
        return ResponseEntity.ok(convertToDto(rawTicket));
    }

    // --- 2. SECURE HISTORY WITH DTO ---
    @GetMapping("/history")
    public ResponseEntity<List<TicketResponse>> getUserBookingHistory(Principal principal) {
        
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Ticket> rawTickets = ticketService.getBookingHistory(user.getId());

        // Convert the whole list to DTOs using Java Streams
        List<TicketResponse> safeResponse = rawTickets.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(safeResponse);
    }

    // --- Helper Method to Map Entity to DTO ---
    private TicketResponse convertToDto(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getPnr(),
                ticket.getTrain().getTrainNumber(),
                ticket.getTrain().getSourceStation(),
                ticket.getTrain().getDestinationStation(),
                ticket.getSeatClass(), 
                ticket.getSeatNumber(),
                ticket.getStatus(),
                ticket.getTotalFare()
        );
    }
}