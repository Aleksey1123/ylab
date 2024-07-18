package org.example.repository;

import org.example.entity.Booking;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/** An interface for BookingRepositoryJDBC **/
public interface BookingRepository {

    Booking save(Booking booking) throws SQLException;

    Booking deleteById(Integer bookingId) throws SQLException;

    List<Booking> findAllBookingsByDate(LocalDateTime date) throws SQLException;

    List<Booking> findAllBookingsByUser(String username) throws SQLException;

    List<Booking> findAllBookingsByResource(Integer resourceId) throws SQLException;

    Map<Integer, Booking> findAllBookings() throws SQLException;
}
