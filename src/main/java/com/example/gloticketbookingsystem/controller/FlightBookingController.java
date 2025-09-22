package com.example.gloticketbookingsystem.controller;

import com.example.gloticketbookingsystem.entity.FlightBooking;
import com.example.gloticketbookingsystem.service.FlightBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class FlightBookingController {

    private final FlightBookingService bookingService;

    public FlightBookingController(FlightBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/passengerId/{passengerId}")
    public ResponseEntity<List<FlightBooking>> getBookingsByPassenger(@PathVariable Long passengerId) {
        List<FlightBooking> bookings = bookingService.getBookingsByPassenger(passengerId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<FlightBooking> getBookingById(@PathVariable Long bookingId) {
        FlightBooking booking = bookingService.getBookingById(bookingId);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FlightBooking> createBooking(@Valid @RequestBody FlightBooking booking) {
        FlightBooking createdBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<FlightBooking> updateBooking(@PathVariable Long bookingId,
                                                       @Valid @RequestBody FlightBooking booking) {
        FlightBooking updatedBooking = bookingService.updateBooking(bookingId, booking);
        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
