package com.example.gloticketbookingsystem.service;

import com.example.gloticketbookingsystem.entity.FlightBooking;

import java.util.List;

public interface FlightBookingService {

    FlightBooking createBooking(FlightBooking booking);

    FlightBooking updateBooking(Long bookingId, FlightBooking booking);

    FlightBooking getBookingById(Long bookingId);

    List<FlightBooking> getBookingsByPassenger(Long passengerId);

    void deleteBooking(Long bookingId);
}
