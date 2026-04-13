package com.shreyass.irctc_clone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shreyass.irctc_clone.model.ACTicket;
import com.shreyass.irctc_clone.model.SleeperTicket;
import com.shreyass.irctc_clone.model.Ticket;
import com.shreyass.irctc_clone.model.Train;
import com.shreyass.irctc_clone.model.User;
import com.shreyass.irctc_clone.repository.TicketRepository;
import com.shreyass.irctc_clone.repository.TrainRepository;
import com.shreyass.irctc_clone.repository.UserRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TrainRepository trainRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Ticket bookTicket(Long userId, Long trainId, String seatClass) {
        
        // 1. Fetch the Train using the LOCKED method
        // If another user is booking this train right now, this line will pause and wait its turn!
        Train train = trainRepository.findByIdWithPessimisticLock(trainId)
                .orElseThrow(() -> new RuntimeException("Train not found!"));

        // 2. Fetch the User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // 3. Check for available seats
        if (train.getAvailableSeats() <= 0) {
            throw new RuntimeException("Sorry, this train is fully booked.");
        }

        // 4. Create the new Ticket
        Ticket ticket;
        if (seatClass.equalsIgnoreCase("AC")) {
            ticket = new ACTicket();
        } else if (seatClass.equalsIgnoreCase("SLEEPER")) {
            ticket = new SleeperTicket();
        } else {
            throw new RuntimeException("Invalid seat class. Choose 'AC' or 'SLEEPER'.");
        }

        ticket.setTrain(train);
        ticket.setUser(user);
        ticket.setStatus("CONFIRMED");

        String generatedPnr = String.valueOf((long)(Math.random() * 9000000000L) + 1000000000L);
        ticket.setPnr(generatedPnr);
        
        // Let the object calculate its own price!
        ticket.calculateFare(train.getBaseFare());

        // 5. Assign seat number
        int assignedSeat = train.getTotalSeats() - train.getAvailableSeats() + 1;
        ticket.setSeatNumber(assignedSeat);
        ticket.setBerthPreference("WINDOW"); 
        ticket.setAllocatedBerthType("WINDOW");

        // 6. Deduct a seat and save the train
        train.setAvailableSeats(train.getAvailableSeats() - 1);
        trainRepository.save(train);

        // 7. Save the Ticket
        // Once this method successfully returns, Spring automatically unlocks the row in Postgres!
        return ticketRepository.save(ticket);
    }

    // Fetch booking history
    public List<Ticket> getBookingHistory(Long userId) {
        return ticketRepository.findByUser_Id(userId);
    }
}

