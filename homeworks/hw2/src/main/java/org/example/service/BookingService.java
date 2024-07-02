package org.example.service;

import org.example.entity.Booking;
import org.example.entity.User;
import org.example.repository.BookingRepositoryJDBC;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


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
            if (user == null) {
                System.out.println("You must log in before booking.");
                return false;
            }

            if (!resourceType.equals("W") && !resourceType.equals("H")) {
                System.out.println("Invalid resource type: " + resourceType);
                return false;
            }

            LocalDateTime startTime = LocalDateTime.parse(startDateTimeString);
            LocalDateTime endTime = LocalDateTime.parse(endDateTimeString);
            Booking booking = Booking.builder()
                    .workplaceId(Integer.valueOf(resourceId))
                    .startTime(startTime)
                    .endTime(endTime)
                    .user(user)
                    .build();

            List<Booking> conflictList = repository.findAllBookingsByResource(Integer.valueOf(resourceId))
                    .stream()
                    .filter(b -> b.getStartTime().isBefore(endTime) && b.getEndTime().isAfter(startTime))
                    .toList();

            if (conflictList.isEmpty()) {
                repository.save(booking);
                return true;
            }
        }
        catch (DateTimeParseException e) {
            System.out.println("Incorrect date entered.");
        }
        catch (NumberFormatException e) {
            System.out.println("All entered ID's must be Integer type.");
        }

        return false;
    }

    /**
     * Cancels booking. Method throws a RuntimeException in case of incorrect booking ID.
     */
    public boolean cancelBooking(String bookingId) {

        try {
            if (repository.deleteById(Integer.valueOf(bookingId)) == null) {
                System.out.println("Incorrect booking ID, please try again.");
                return false;
            }

            return true;
        }
        catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    /** Returns all bookings of the current day. **/
    public List<Booking> getAllBookingsByDate(String startDateTimeString) {
        try {
            LocalDateTime date = LocalDateTime.parse(startDateTimeString);
            return repository.findAllBookingsByDate(date);
        }
        catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Booking> getAllBookingsByUser(String username) {

        if (userService.findUserByUsername(username) != null)
            return repository.findAllBookingsByUser(username);

        return null;
    }

    public List<Booking> getAllBookingsByResource(String resourceId) {

        try {
            return repository.findAllBookingsByResource(Integer.valueOf(resourceId));
        }
        catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Booking> getAllBookings() {

        return new ArrayList<>(repository.findAllBookings().values());
    }
}
