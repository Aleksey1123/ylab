package org.example.service;

import org.example.entity.Booking;
import org.example.entity.User;
import org.example.enums.Resource;
import org.example.repository.BookingRepositoryJDBC;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/** This service corresponds for work with Bookings Entities. **/
public class BookingService {

    private BookingRepositoryJDBC repository;

    public BookingService() {
        repository = new BookingRepositoryJDBC();
    }

    public List<Booking> makeBooking(User user, String workplaceId, String hallId, LocalDateTime startTime, LocalDateTime endTime) {
        UUID resourceUUID;
        String resourceType;

        if (workplaceId != null && hallId == null) {
            resourceUUID = UUID.fromString(workplaceId);
            resourceType = Resource.WORKPLACE.toString();
        } else if (hallId != null && workplaceId == null) {
            resourceUUID = UUID.fromString(hallId);
            resourceType = Resource.HALL.toString();
        } else {
            throw new IllegalArgumentException("Exactly one of workplaceId or hallId must be non-null");
        }

        List<Booking> conflictList = repository.findAllBookingsByResource(resourceUUID.toString())
                .stream()
                .filter(b -> b.getStartTime().isBefore(endTime) && b.getEndTime().isAfter(startTime))
                .toList();

        Booking booking;
        if (resourceType.equalsIgnoreCase(Resource.WORKPLACE.toString())) {
            booking = Booking.builder()
                    .id(UUID.randomUUID())
                    .workplaceId(resourceUUID)
                    .startTime(startTime)
                    .endTime(endTime)
                    .user(user)
                    .build();
        } else if (resourceType.equalsIgnoreCase(Resource.HALL.toString())) {
            booking = Booking.builder()
                    .id(UUID.randomUUID())
                    .hallId(resourceUUID)
                    .startTime(startTime)
                    .endTime(endTime)
                    .user(user)
                    .build();
        } else {
            throw new IllegalArgumentException("Invalid resource type: " + resourceType);
        }

        if (conflictList.isEmpty()) {
            repository.save(booking);
        }

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
