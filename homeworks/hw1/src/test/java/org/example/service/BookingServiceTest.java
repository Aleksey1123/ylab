package org.example.service;

import org.example.entity.Booking;
import org.example.entity.User;
import org.example.entity.Workplace;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class BookingServiceTest {

    private BookingService service;
    private User user;
    private Workplace workplace;
    private UUID resourceId;

    @BeforeEach
    void setUp() {

        service = new BookingService();
        resourceId = UUID.randomUUID();

        user = User.builder()
                .id(UUID.randomUUID())
                .username("test")
                .password("test")
                .build();

        workplace = Workplace.builder()
                .id(resourceId)
                .description("test")
                .build();
    }

    @Test
    void testCancelBooking() {

        LocalDateTime startTime = LocalDateTime.of(2024, 6, 21, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 6, 21, 12, 0);

        service.makeBooking(user, workplace.getId().toString(), startTime, endTime);
        List<Booking> bookings = service.getAllBookings();
        assertThat(bookings).hasSize(1);

        String bookingId = bookings.get(0).getId().toString();
        service.cancelBooking(bookingId);

        bookings = service.getAllBookings();
        assertThat(bookings).isEmpty();
    }

    @Test
    void testMakeBookingWithNoConflict() {

        LocalDateTime startTime = LocalDateTime.of(2024, 6, 21, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 6, 21, 12, 0);

        service.makeBooking(user, workplace.getId().toString(), startTime, endTime);

        List<Booking> bookings = service.getAllBookings();
        assertThat(bookings).hasSize(1);
        Booking booking = bookings.get(0);

        assertThat(booking.getResourceId()).isEqualTo(resourceId);
        assertThat(booking.getStartTime()).isEqualTo(startTime);
        assertThat(booking.getEndTime()).isEqualTo(endTime);
        assertThat(booking.getUser()).isEqualTo(user);
    }

    @Test
    void testGetAllBookings() {

        List<Booking> bookings = service.getAllBookings();
        assertThat(bookings).hasSize(0);
    }

    @Test
    void testMakeBookingWithConflict() {

        LocalDateTime startTime1 = LocalDateTime.of(2024, 7, 21, 10, 0);
        LocalDateTime endTime1 = LocalDateTime.of(2024, 7, 21, 12, 0);

        LocalDateTime startTime2 = LocalDateTime.of(2024, 7, 21, 11, 0);
        LocalDateTime endTime2 = LocalDateTime.of(2024, 7, 21, 13, 0);

        service.makeBooking(user, workplace.getId().toString(), startTime1, endTime1);
        service.makeBooking(user, workplace.getId().toString(), startTime2, endTime2);

        List<Booking> bookings = service.getAllBookings();
        assertThat(bookings).hasSize(1);
    }

}