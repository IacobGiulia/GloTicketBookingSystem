package com.example.gloticketbookingsystem.service;

import com.example.gloticketbookingsystem.entity.FlightBooking;
import com.example.gloticketbookingsystem.exception.BookingNotFoundException;
import com.example.gloticketbookingsystem.exception.FlightNotFoundException;
import com.example.gloticketbookingsystem.exception.PassengerNotFoundException;
import com.example.gloticketbookingsystem.repository.FlightBookingRepository;
import com.example.gloticketbookingsystem.repository.FlightRepository;
import com.example.gloticketbookingsystem.repository.PassengerRepository;
import com.example.gloticketbookingsystem.service.FlightBookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FlightBookingServiceImpl implements FlightBookingService {

    private final FlightBookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;

    public FlightBookingServiceImpl(FlightBookingRepository bookingRepository,
                                    FlightRepository flightRepository,
                                    PassengerRepository passengerRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public FlightBooking createBooking(FlightBooking booking) {

        if (!flightRepository.existsById(booking.getFlightId())) {
            throw new FlightNotFoundException("Flight not found with id: " + booking.getFlightId());
        }
        if (!passengerRepository.existsById(booking.getPassengerId())) {
            throw new PassengerNotFoundException("Passenger not found with id: " + booking.getPassengerId());
        }

        if (bookingRepository.existsById(booking.getBookingId())) {
            throw new IllegalArgumentException("Booking ID already exists: " + booking.getBookingId());
        }

        return bookingRepository.save(booking);
    }

    @Override
    public FlightBooking updateBooking(Long bookingId, FlightBooking booking) {
        FlightBooking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + bookingId));

        existingBooking.setFlightId(booking.getFlightId());
        existingBooking.setPassengerId(booking.getPassengerId());
        existingBooking.setBookingDate(booking.getBookingDate());
        existingBooking.setTotalPrice(booking.getTotalPrice());

        return bookingRepository.save(existingBooking);
    }

    @Override
    public FlightBooking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + bookingId));
    }

    @Override
    public List<FlightBooking> getBookingsByPassenger(Long passengerId) {
        return bookingRepository.findByPassengerId(passengerId);
    }

    @Override
    public void deleteBooking(Long bookingId) {
        FlightBooking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + bookingId));
        bookingRepository.delete(existingBooking);
    }
}
