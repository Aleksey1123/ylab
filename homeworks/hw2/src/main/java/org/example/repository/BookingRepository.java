package org.example.repository;

import org.example.entity.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BookingRepository {

    Booking save(Booking booking);

    Booking deleteById(Integer bookingId);

    List<Booking> findAllBookingsByDate(LocalDateTime date);

    List<Booking> findAllBookingsByUser(String username);

    List<Booking> findAllBookingsByResource(Integer resourceId);

    Map<Integer, Booking> findAllBookings();
}
