package com.shreyass.irctc_clone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shreyass.irctc_clone.model.Ticket;
import com.shreyass.irctc_clone.service.TicketService;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // We use @RequestParam here to pass data in the URL (e.g., ?userId=1&trainId=1)
    @PostMapping("/book")
    public Ticket bookTrainTicket(@RequestParam Long userId, @RequestParam Long trainId, @RequestParam String seatClass) {
        return ticketService.bookTicket(userId, trainId, seatClass);
    }

    // GET request to fetch all tickets for a specific user
    // Example URL: /tickets/history/1
    @GetMapping("/history/{userId}")
    public List<Ticket> getUserBookingHistory(@PathVariable Long userId) {
        return ticketService.getBookingHistory(userId);
    }
}