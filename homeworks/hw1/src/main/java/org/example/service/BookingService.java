package org.example.service;

import org.example.entity.Booking;
import org.example.entity.User;

import java.time.LocalDateTime;
import java.util.*;

/** Данный сервис отвечает за работу с сущностью бронирование.
 *  Когда бронируется какой-либо ресурс создаётся и бронирование.
 *  Все бронирования хранятся в HashMap bookings (хранятся как <bookingId, booking>).
 *  В сервисе присутствует конструктор, который инициализирует HashMap bookings.
 **/
public class BookingService {

    // bookingId: booking
    private final Map<String, Booking> bookings;

    public BookingService() {
         bookings = new HashMap<>();
    }

    public List<Booking> makeBooking(User user, String resourceId,
                               LocalDateTime startTime, LocalDateTime endTime) {

        List<Booking> conflictList = bookings.values()
                .stream()
                .filter(b -> b.getResourceId().toString().equals(resourceId) &&
                        b.getStartTime().isBefore(endTime) &&
                        b.getEndTime().isAfter(startTime))
                .toList();

        Booking booking = Booking.builder()
                .id(UUID.randomUUID())
                .resourceId(UUID.fromString(resourceId))
                .startTime(startTime)
                .endTime(endTime)
                .user(user)
                .build();

        if (conflictList.isEmpty())
            bookings.put(booking.getId().toString(), booking);

        return conflictList;
    }

    public boolean cancelBooking(String bookingId) {

        return bookings.remove(bookingId) != null;
    }

    public List<Booking> getAllBookingsByDate(LocalDateTime date) {

        return bookings.values()
                .stream()
                .filter(b -> b.getStartTime().isBefore(date) || b.getEndTime().isAfter(date))
                .toList();
    }

    public List<Booking> getAllBookingsByUser(String username) {

        return bookings.values()
                .stream()
                .filter(b -> b.getUser().getUsername().equals(username))
                .toList();
    }

    public List<Booking> getAllBookingsByResource(String resourceId) {

        return bookings.values()
                .stream()
                .filter(b -> b.getResourceId().toString().equals(resourceId))
                .toList();
    }

    public List<Booking> getAllBookings() {

        return new ArrayList<>(bookings.values());
    }
}
