package com.example.gloticketbookingsystem.repository;

import com.example.gloticketbookingsystem.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    boolean existsByPassengerId(Long passengerId);
}
