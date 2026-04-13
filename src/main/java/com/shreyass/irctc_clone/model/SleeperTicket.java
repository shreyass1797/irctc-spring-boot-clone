package com.shreyass.irctc_clone.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SLEEPER") // This saves "SLEEPER" in the database column
public class SleeperTicket extends Ticket {

    @Override
    public void calculateFare(Double trainBaseFare) {
        // Sleeper is standard pricing: Just the base fare + 50 INR reservation fee
        Double calculatedPrice = trainBaseFare + 50.0;
        this.setTotalFare(calculatedPrice);
    }
}