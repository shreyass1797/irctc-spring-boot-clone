package com.shreyass.irctc_clone.model;

import java.math.BigDecimal;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("SLEEPER") 
@Getter
@Setter
@NoArgsConstructor
public class SleeperTicket extends Ticket {

    // IMPROVEMENT 3: Removing Magic Numbers
    private static final BigDecimal RESERVATION_FEE = new BigDecimal("50.00");

    @Override
    public void calculateFare(BigDecimal trainBaseFare) {
        // Just the base fare + 50 INR
        BigDecimal calculatedPrice = trainBaseFare.add(RESERVATION_FEE);
        this.setTotalFare(calculatedPrice);
    }
}