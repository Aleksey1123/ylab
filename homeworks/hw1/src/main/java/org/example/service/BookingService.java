package org.example.service;

import org.example.entity.Booking;
import org.example.entity.User;
import org.example.repository.BookingRepository;
import org.example.repository.BookingRepositoryImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Данный сервис отвечает за работу с сущностью бронирование.
 *  Когда бронируется какой-либо ресурс создаётся и бронирование.
 *  Все бронирования хранятся в HashMap bookings (хранятся как <bookingId, booking>).
 *  В сервисе присутствует конструктор, который инициализирует HashMap bookings.
 **/
public class BookingService {

    private BookingRepository repository;

    public BookingService() {
        repository = new BookingRepositoryImpl();
    }

    public List<Booking> makeBooking(User user, String resourceId,
                               LocalDateTime startTime, LocalDateTime endTime) {

        List<Booking> conflictList = repository.findAllBookings().values()
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
            repository.save(booking);

        return conflictList;
    }

    public boolean cancelBooking(String bookingId) {

        return repository.deleteById(bookingId) != null;
    }

    public List<Booking> getAllBookingsByDate(LocalDateTime date) {

        return repository.findAllBookingsByDate(date);
    }

    public List<Booking> getAllBookingsByUser(String username) {

        return repository.findAllBookingsByUser(username);
    }

    public List<Booking> getAllBookingsByResource(String resourceId) {

        return repository.findAllBookingsByResource(resourceId);
    }

    public List<Booking> getAllBookings() {

        return new ArrayList<>(repository.findAllBookings().values());
    }
}
