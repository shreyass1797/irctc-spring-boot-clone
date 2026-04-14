package com.shreyass.irctc_clone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shreyass.irctc_clone.model.Train;
import com.shreyass.irctc_clone.repository.TrainRepository;

@Service // Tells Spring: "This is where my business logic lives"
public class TrainService {

    // @Autowired tells Spring to automatically grab the TrainRepository we just made and plug it in here.
    @Autowired
    private TrainRepository trainRepository;

    // Logic to get all trains
    public List<Train> getAllTrains() {
        return trainRepository.findAll(); // This runs SELECT * FROM trains
    }

    // Logic to add a new train to the database (For Admins)
    public Train addTrain(Train train) {
        // When a new train is added, available seats should equal total seats
        train.setAvailableSeats(train.getTotalSeats());
        return trainRepository.save(train); // runs INSERT INTO trains...
    }

    public List<Train> searchTrains(String source, String destination) {
        return trainRepository.findBySourceStationAndDestinationStation(source, destination);
    }
}