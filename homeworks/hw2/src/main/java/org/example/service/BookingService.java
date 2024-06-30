package org.example.service;

import org.example.entity.Booking;
import org.example.entity.User;
import org.example.repository.BookingRepositoryJDBC;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/** This service corresponds for work with Bookings Entities. **/
public class BookingService {

    private UserService userService;
    private BookingRepositoryJDBC repository;

    public BookingService(UserService userService) {
        this.userService = userService;
        repository = new BookingRepositoryJDBC();
    }

    /**
     * Booking a resource. Method throws DateTimeException in case of incorrect date entry.
     * Method throws a RuntimeException in cases: if the user is not logged in,
     * if there is a booking conflict, in case of conflict makeBooking() returns false,
     * otherwise - true.
     */
    public boolean makeBooking(String resourceId, String resourceType,
                               String startDateTimeString, String endDateTimeString) {

        try {
            User user = userService.isAuthorised();
            if (user == null)
                throw new RuntimeException("You must log in before booking.");

            if (!resourceType.equals("W") && !resourceType.equals("H"))
                throw new RuntimeException("Invalid resource type: " + resourceType);

            LocalDateTime startTime = LocalDateTime.parse(startDateTimeString);
            LocalDateTime endTime = LocalDateTime.parse(endDateTimeString);
            Booking booking = Booking.builder()
                    .id(UUID.randomUUID())
                    .workplaceId(UUID.fromString(resourceId))
                    .startTime(startTime)
                    .endTime(endTime)
                    .user(user)
                    .build();

            List<Booking> conflictList = repository.findAllBookingsByResource(resourceId)
                    .stream()
                    .filter(b -> b.getStartTime().isBefore(endTime) && b.getEndTime().isAfter(startTime))
                    .toList();

            if (conflictList.isEmpty()) {
                repository.save(booking);
                return true;
            }
        }
        catch (DateTimeException exception) {
            System.out.println("Incorrect date entered.");
        }
        catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }

        return false;
    }

    /**
     * Cancels booking. Method throws a RuntimeException in case of incorrect booking ID.
     */
    public boolean cancelBooking(String bookingId) {

        try {
            if (repository.deleteById(bookingId) == null)
                throw new RuntimeException("Incorrect booking ID, please try again.");

            return true;
        }
        catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }

        return false;
    }

    /** Returns all bookings of the current day. **/
    public List<Booking> getAllBookingsByDate(String startDateTimeString) {
        try {
            LocalDateTime date = LocalDateTime.parse(startDateTimeString);
            return repository.findAllBookingsByDate(date);
        }
        catch (DateTimeException exception) {
            System.out.println(exception.getMessage());
        }

        return null;
    }

    public List<Booking> getAllBookingsByUser(String username) {

        if (userService.findUserByUsername(username) != null)
            return repository.findAllBookingsByUser(username);

        return null;
    }

    public List<Booking> getAllBookingsByResource(String resourceId) {

        return repository.findAllBookingsByResource(resourceId);
    }

    public List<Booking> getAllBookings() {

        return new ArrayList<>(repository.findAllBookings().values());
    }
}
