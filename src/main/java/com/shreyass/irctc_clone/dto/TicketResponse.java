package com.shreyass.irctc_clone.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; // Add this import

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private Long ticketId;
    private String pnr;
    private String trainNumber;
    private String sourceStation;
    private String destinationStation;
    private String seatClass;
    private int seatNumber;
    private String status;
    private BigDecimal fare; 
}