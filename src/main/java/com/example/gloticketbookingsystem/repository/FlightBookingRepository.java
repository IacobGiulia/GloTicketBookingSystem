package com.example.gloticketbookingsystem.repository;

import com.example.gloticketbookingsystem.entity.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {

    List<FlightBooking> findByPassengerId(Long passengerId);

    boolean existsByBookingId(Long bookingId);

    boolean existsByFlightId(Long flightId);
}
