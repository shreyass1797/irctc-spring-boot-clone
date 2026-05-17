package com.shreyass.irctc_clone.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trains")
@Getter
@Setter
@NoArgsConstructor
public class Train implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    private String trainNumber;
    private String sourceStation;
    private String destinationStation;
    private int totalSeats;
    private int availableSeats;
    private BigDecimal baseFare;
    
}