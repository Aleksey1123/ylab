package org.example.repository;

import org.example.entity.Booking;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingRepositoryImpl implements BookingRepository {

    // bookingId: booking
    private Map<String, Booking> bookings;

    public BookingRepositoryImpl() {
        bookings = new HashMap<>();
    }

    @Override
    public Booking save(Booking booking) {

        return bookings.put(booking.getId().toString(), booking);
    }

    @Override
    public Booking deleteById(String bookingId) {

        return bookings.remove(bookingId);
    }

    public List<Booking> findAllBookingsByDate(LocalDateTime date) {

        return bookings.values()
                .stream()
                .filter(b -> b.getStartTime().isBefore(date) || b.getEndTime().isAfter(date))
                .toList();
    }

    public List<Booking> findAllBookingsByUser(String username) {

        return bookings.values()
                .stream()
                .filter(b -> b.getUser().getUsername().equals(username))
                .toList();
    }

    public List<Booking> findAllBookingsByResource(String resourceId) {

        return bookings.values()
                .stream()
                .filter(b -> b.getResourceId().toString().equals(resourceId))
                .toList();
    }

    public Map<String, Booking> findAllBookings() {

        return bookings;
    }
}
