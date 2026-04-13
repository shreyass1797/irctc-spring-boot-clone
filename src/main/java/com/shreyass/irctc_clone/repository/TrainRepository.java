package com.shreyass.irctc_clone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shreyass.irctc_clone.model.Train;

import jakarta.persistence.LockModeType;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    // By extending JpaRepository, Spring automatically gives you 
    // methods like save(), findAll(), and findById() for your Trains.
    List<Train> findBySourceStationAndDestinationStation(String source, String destination);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM Train t WHERE t.id = :trainId")
    Optional<Train> findByIdWithPessimisticLock(@Param("trainId") Long id);
}
