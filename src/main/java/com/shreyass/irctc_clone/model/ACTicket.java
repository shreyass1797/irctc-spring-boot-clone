package com.shreyass.irctc_clone.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("AC") // This saves "AC" in the database column
public class ACTicket extends Ticket {

    @Override
    public void calculateFare(Double trainBaseFare) {
        // AC Class is expensive: Base fare x 2.5 + 150 INR AC charge
        Double calculatedPrice = (trainBaseFare * 2.5) + 150.0;
        this.setTotalFare(calculatedPrice);
    }
}