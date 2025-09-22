package com.example.gloticketbookingsystem.service;

import com.example.gloticketbookingsystem.entity.FlightBooking;
import com.example.gloticketbookingsystem.exception.BookingNotFoundException;
import com.example.gloticketbookingsystem.exception.FlightNotFoundException;
import com.example.gloticketbookingsystem.exception.PassengerNotFoundException;
import com.example.gloticketbookingsystem.repository.FlightBookingRepository;
import com.example.gloticketbookingsystem.service.FlightBookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional
public class FlightBookingServiceImpl implements FlightBookingService {

    private final FlightBookingRepository bookingRepository;
    private final RestTemplate restTemplate;


    private static final String FLIGHT_SERVICE_URL = "http://localhost:8081/flights/{flightId}";
    private static final String PASSENGER_SERVICE_URL = "http://localhost:8082/passengers/{passengerId}";

    public FlightBookingServiceImpl(FlightBookingRepository bookingRepository, RestTemplate restTemplate) {
        this.bookingRepository = bookingRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public FlightBooking createBooking(FlightBooking booking) {

        try {
            restTemplate.getForObject(FLIGHT_SERVICE_URL, Object.class, booking.getFlightId());
        } catch (RestClientException e) {
            throw new FlightNotFoundException("Flight not found with id: " + booking.getFlightId());
        }

        try {
            restTemplate.getForObject(PASSENGER_SERVICE_URL, Object.class, booking.getPassengerId());
        } catch (RestClientException e) {
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
