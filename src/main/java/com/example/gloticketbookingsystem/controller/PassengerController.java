package com.example.gloticketbookingsystem.controller;

import com.example.gloticketbookingsystem.entity.Passenger;
import com.example.gloticketbookingsystem.exception.PassengerNotFoundException;
import com.example.gloticketbookingsystem.repository.PassengerRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerRepository passengerRepository;

    public PassengerController(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        return ResponseEntity.ok(passengerRepository.findAll());
    }

    @GetMapping("/{passengerId}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger not found with id: " + passengerId));
        return ResponseEntity.ok(passenger);
    }

    @PostMapping
    public ResponseEntity<Passenger> createPassenger(@Valid @RequestBody Passenger passenger) {
        return ResponseEntity.ok(passengerRepository.save(passenger));
    }

    @PutMapping("/{passengerId}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long passengerId,
                                                     @Valid @RequestBody Passenger passenger) {
        Passenger existingPassenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger not found with id: " + passengerId));

        existingPassenger.setFirstName(passenger.getFirstName());
        existingPassenger.setLastName(passenger.getLastName());
        existingPassenger.setEmail(passenger.getEmail());

        return ResponseEntity.ok(passengerRepository.save(existingPassenger));
    }

    @DeleteMapping("/{passengerId}")
    public ResponseEntity<String> deletePassenger(@PathVariable Long passengerId) {
        Passenger existingPassenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger not found with id: " + passengerId));
        passengerRepository.delete(existingPassenger);
        return ResponseEntity.ok("Passenger deleted successfully with ID: " + passengerId);
    }
}
