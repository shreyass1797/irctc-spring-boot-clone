package com.shreyass.irctc_clone.controller; 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shreyass.irctc_clone.model.Train;
import com.shreyass.irctc_clone.service.TrainService;

@RestController
@RequestMapping("/api/trains") // This means ALL endpoints in this file start with /trains
public class TrainController {

    @Autowired
    private TrainService trainService;

    // POST /trains -> Allows us to send JSON data to create a new train
    @PostMapping
    public Train createNewTrain(@RequestBody Train train) {
        return trainService.addTrain(train);
    }

    @GetMapping("/search")
    public List<Train> searchForTrains(
            @RequestParam String source, 
            @RequestParam String destination) {
        return trainService.searchTrains(source, destination);
    }
}