package org.example.service;

import org.example.entity.Booking;
import org.example.entity.User;
import org.example.exception.InvalidIdException;
import org.example.exception.NotAuthorizedException;
import org.example.model.BookingPostRequest;
import org.example.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    private User testUser;
    private BookingPostRequest bookingRequest;
    private Booking booking;

    @BeforeEach
    void setUp() {
        testUser = new User();
        bookingRequest = new BookingPostRequest();
        bookingRequest.setResourceType("W");
        bookingRequest.setResourceId("1");
        bookingRequest.setStartDateTimeString("2024-06-21T15:00:00");
        bookingRequest.setEndDateTimeString("2024-06-21T16:00:00");

        booking = Booking.builder()
                .workplaceId(1)
                .hallId(null)
                .startTime(LocalDateTime.parse("2024-06-21T15:00:00"))
                .endTime(LocalDateTime.parse("2024-06-21T16:00:00"))
                .user(testUser)
                .build();
    }

    @Test
    void testMakeBookingSuccess() throws SQLException {
        when(userService.isAuthorised()).thenReturn(testUser);
        when(bookingRepository.findAllBookingsByResource(anyInt())).thenReturn(Collections.emptyList());
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking result = bookingService.makeBooking(bookingRequest);
        assertNotNull(result);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testMakeBookingUnauthorized() throws SQLException {
        when(userService.isAuthorised()).thenReturn(null);

        assertThrows(NotAuthorizedException.class, () -> bookingService.makeBooking(bookingRequest));
        verify(bookingRepository, times(0)).save(any(Booking.class));
    }

    @Test
    void testMakeBookingConflict() throws SQLException {
        when(userService.isAuthorised()).thenReturn(testUser);
        Booking conflictBooking = Booking.builder()
                .workplaceId(1)
                .startTime(LocalDateTime.parse("2024-06-21T14:30:00"))
                .endTime(LocalDateTime.parse("2024-06-21T15:30:00"))
                .build();
        when(bookingRepository.findAllBookingsByResource(anyInt())).thenReturn(List.of(conflictBooking));

        Booking result = bookingService.makeBooking(bookingRequest);
        assertNull(result);
        verify(bookingRepository, times(0)).save(any(Booking.class));
    }

    @Test
    void testCancelBookingSuccess() throws SQLException {
        when(bookingRepository.deleteById(anyInt())).thenReturn(booking);

        Booking result = bookingService.cancelBooking("1");
        assertNotNull(result);
        verify(bookingRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testCancelBookingInvalidId() throws SQLException {

        assertThrows(InvalidIdException.class, () -> bookingService.cancelBooking(null));
        verify(bookingRepository, times(0)).deleteById(anyInt());
    }

    @Test
    void testGetAllBookingsByDateSuccess() throws SQLException {
        List<Booking> bookings = List.of(booking);
        when(bookingRepository.findAllBookingsByDate(any(LocalDateTime.class))).thenReturn(bookings);

        List<Booking> result = bookingService.getAllBookingsByDate("2024-06-21T12:00:00");
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookingRepository, times(1)).findAllBookingsByDate(any(LocalDateTime.class));
    }

    @Test
    void testGetAllBookingsByUserSuccess() throws SQLException {
        List<Booking> bookings = List.of(booking);
        when(bookingRepository.findAllBookingsByUser(anyString())).thenReturn(bookings);

        List<Booking> result = bookingService.getAllBookingsByUser("admin");
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookingRepository, times(1)).findAllBookingsByUser(anyString());
    }


    @Test
    void testGetAllBookingsByResourceSuccess() throws SQLException {
        List<Booking> bookings = List.of(booking);
        when(bookingRepository.findAllBookingsByResource(anyInt())).thenReturn(bookings);

        List<Booking> result = bookingService.getAllBookingsByResource("1");
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookingRepository, times(1)).findAllBookingsByResource(anyInt());
    }


    @Test
    void testGetAllBookings() throws SQLException {
        when(bookingRepository.findAllBookings()).thenReturn(Collections.singletonMap(1, booking));

        List<Booking> result = bookingService.getAllBookings();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookingRepository, times(1)).findAllBookings();
    }
}