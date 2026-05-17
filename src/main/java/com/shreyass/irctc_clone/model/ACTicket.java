package com.shreyass.irctc_clone.model;

import java.math.BigDecimal;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("AC") 
@Getter
@Setter
@NoArgsConstructor
public class ACTicket extends Ticket {

    private static final BigDecimal AC_MULTIPLIER = new BigDecimal("2.5");
    private static final BigDecimal AC_FLAT_CHARGE = new BigDecimal("150.00");

    @Override
    public void calculateFare(BigDecimal trainBaseFare) {
        // Base fare * 2.5 + 150
        BigDecimal calculatedPrice = trainBaseFare.multiply(AC_MULTIPLIER).add(AC_FLAT_CHARGE);
        this.setTotalFare(calculatedPrice);
    }
}