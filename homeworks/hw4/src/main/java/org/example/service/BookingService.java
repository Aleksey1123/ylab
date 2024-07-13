package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.annotation.Loggable;
import org.example.entity.Booking;
import org.example.entity.User;
import org.example.exception.InvalidIdException;
import org.example.exception.InvalidResourceTypeException;
import org.example.exception.NotAuthorizedException;
import org.example.model.BookingPostRequest;
import org.example.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/** This service corresponds for work with Bookings Entities. **/
@Service
@RequiredArgsConstructor
public class BookingService {

    private final UserService userService;
    private final BookingRepository bookingRepository;

    /**
     * Booking a resource. Method throws DateTimeException in case of incorrect date entry.
     * Method throws a RuntimeException in cases: if the user is not logged in,
     * if there is a booking conflict, in case of conflict makeBooking() returns false,
     * otherwise - true.
     */
    @Loggable
    public Booking makeBooking(BookingPostRequest bookingRequest) throws SQLException {

        User user = userService.isAuthorised();
        if (user == null) {
            throw new NotAuthorizedException("You must log in before booking.");
        }

        String resourceType = bookingRequest.getResourceType();
        int resourceId = Integer.parseInt(bookingRequest.getResourceId());
        LocalDateTime startTime = LocalDateTime.parse(bookingRequest.getStartDateTimeString());
        LocalDateTime endTime = LocalDateTime.parse(bookingRequest.getEndDateTimeString());
        Booking booking;

        if (!resourceType.equals("W") && !resourceType.equals("H")) {
            throw new InvalidResourceTypeException("Invalid resource type: " + resourceType + ".");
        }

        if (resourceType.equals("W")) {
            booking = Booking.builder()
                    .workplaceId(resourceId)
                    .hallId(null)
                    .startTime(startTime)
                    .endTime(endTime)
                    .user(user)
                    .build();
        }
        else {
            booking = Booking.builder()
                    .workplaceId(null)
                    .hallId(resourceId)
                    .startTime(startTime)
                    .endTime(endTime)
                    .user(user)
                    .build();
        }

        List<Booking> conflictList = bookingRepository.findAllBookingsByResource(resourceId)
                .stream()
                .filter(b -> b.getStartTime().isBefore(endTime) && b.getEndTime().isAfter(startTime))
                .toList();

        if (conflictList.isEmpty()) {
            return bookingRepository.save(booking);
        }

        return null;
    }

    /**
     * Cancels booking. Method throws a RuntimeException in case of incorrect booking ID.
     */
    @Loggable
    public Booking cancelBooking(String bookingId) throws SQLException {

        if (bookingId == null) {
            throw new InvalidIdException("Id must be not null.");
        }

        return bookingRepository.deleteById(Integer.valueOf(bookingId));
    }

    /** Returns all bookings of the current day. **/
    @Loggable
    public List<Booking> getAllBookingsByDate(String startDateTimeString) throws SQLException {

        LocalDateTime date = LocalDateTime.parse(startDateTimeString);
        return bookingRepository.findAllBookingsByDate(date);
    }

    @Loggable
    public List<Booking> getAllBookingsByUser(String username) throws SQLException {

        return bookingRepository.findAllBookingsByUser(username);
    }

    @Loggable
    public List<Booking> getAllBookingsByResource(String resourceId) throws SQLException {

        int id = Integer.parseInt(resourceId);
        return bookingRepository.findAllBookingsByResource(id);
    }

    @Loggable
    public List<Booking> getAllBookings() throws SQLException {

        return new ArrayList<>(bookingRepository.findAllBookings().values());
    }
}
