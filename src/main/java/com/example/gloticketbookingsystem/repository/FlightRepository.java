package com.example.gloticketbookingsystem.repository;

import com.example.gloticketbookingsystem.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    boolean existsByFlightId(Long flightId);
}
