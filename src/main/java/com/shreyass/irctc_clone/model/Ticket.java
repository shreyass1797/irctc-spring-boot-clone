package com.shreyass.irctc_clone.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "tickets")
// 1. Tell Hibernate: "Store all subclasses in this one 'tickets' table"
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// 2. Create a special column to tell us if a row is an AC or Sleeper ticket
@DiscriminatorColumn(name = "seat_class", discriminatorType = DiscriminatorType.STRING)
public abstract class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(unique = true, nullable = false, length = 10)
    private String pnr;

    private int seatNumber;
    private Double totalFare;
    private String berthPreference; // e.g., "WINDOW", "LOWER"
    private String allocatedBerthType;
    private String status; // "CONFIRMED", "WAITLISTED"

    public Ticket() {}

    public abstract void calculateFare(Double trainBaseFare);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getBerthPreference() {
        return berthPreference;
    }

    public void setBerthPreference(String berthPreference) {
        this.berthPreference = berthPreference;
    }

    public String getAllocatedBerthType() {
        return allocatedBerthType;
    }

    public void setAllocatedBerthType(String allocatedBerthType) {
        this.allocatedBerthType = allocatedBerthType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(Double totalFare) {
        this.totalFare = totalFare;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
    
}