package org.example.service;

import org.example.entity.Booking;
import org.example.entity.User;
import org.example.model.BookingPostRequest;
import org.example.repository.BookingRepositoryJDBC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

//class BookingServiceTest {
//
//    private BookingService bookingService;
//    private UserService userService;
//    private BookingRepositoryJDBC bookingRepository;
//
//    @BeforeEach
//    void setUp() {
//        userService = mock(UserService.class);
//        bookingRepository = mock(BookingRepositoryJDBC.class);
//        bookingService = new BookingService(userService);
//        bookingService.repository = bookingRepository;
//    }
//
//    @Test
//    void testMakeBookingSuccess() {
//        User user = new User();
//        when(userService.isAuthorised()).thenReturn(user);
//
//        BookingPostRequest request = new BookingPostRequest();
//        request.setResourceType("W");
//        request.setResourceId("1");
//        request.setStartDateTimeString("2024-06-21T15:00:00");
//        request.setEndDateTimeString("2024-06-21T16:00:00");
//
//        when(bookingRepository.findAllBookingsByResource(anyInt())).thenReturn(Collections.emptyList());
//        when(bookingRepository.save(any(Booking.class))).thenReturn(new Booking());
//
//        Booking result = bookingService.makeBooking(request);
//        assertNotNull(result);
//        verify(bookingRepository, times(1)).save(any(Booking.class));
//    }
//
//    @Test
//    void testMakeBookingUnauthorized() {
//        when(userService.isAuthorised()).thenReturn(null);
//
//        BookingPostRequest request = new BookingPostRequest();
//        Booking result = bookingService.makeBooking(request);
//        assertNull(result);
//        verify(bookingRepository, times(0)).save(any(Booking.class));
//    }
//
//    @Test
//    void testMakeBookingConflict() {
//        User user = new User();
//        when(userService.isAuthorised()).thenReturn(user);
//
//        BookingPostRequest request = new BookingPostRequest();
//        request.setResourceType("W");
//        request.setResourceId("1");
//        request.setStartDateTimeString("2024-06-21T15:00:00");
//        request.setEndDateTimeString("2024-06-21T16:00:00");
//
//        Booking conflictBooking = new Booking();
//        conflictBooking.setStartTime(LocalDateTime.parse("2024-06-21T14:30:00"));
//        conflictBooking.setEndTime(LocalDateTime.parse("2024-06-21T15:30:00"));
//
//        when(bookingRepository.findAllBookingsByResource(anyInt())).thenReturn(List.of(conflictBooking));
//
//        Booking result = bookingService.makeBooking(request);
//        assertNull(result);
//        verify(bookingRepository, times(0)).save(any(Booking.class));
//    }
//
//    @Test
//    void testCancelBookingSuccess() {
//        Booking booking = new Booking();
//        when(bookingRepository.deleteById(anyInt())).thenReturn(booking);
//
//        Booking result = bookingService.cancelBooking("1");
//        assertNotNull(result);
//    }
//
//    @Test
//    void testCancelBookingInvalidId() {
//        when(bookingRepository.deleteById(anyInt())).thenReturn(null);
//
//        Booking result = bookingService.cancelBooking("999");
//        assertNull(result);
//        verify(bookingRepository, times(1)).deleteById(anyInt());
//    }
//
//    @Test
//    void testGetAllBookingsByDateSuccess() {
//        List<Booking> bookings = List.of(new Booking());
//        when(bookingRepository.findAllBookingsByDate(any(LocalDateTime.class))).thenReturn(bookings);
//
//        List<Booking> result = bookingService.getAllBookingsByDate("2024-06-21T12:00:00");
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(bookingRepository, times(1)).findAllBookingsByDate(any(LocalDateTime.class));
//    }
//
//    @Test
//    void testGetAllBookingsByDateInvalidDate() {
//        List<Booking> result = bookingService.getAllBookingsByDate("invalid-date");
//        assertNull(result);
//        verify(bookingRepository, times(0)).findAllBookingsByDate(any(LocalDateTime.class));
//    }
//
//    @Test
//    void testGetAllBookingsByUserSuccess() {
//        User user = new User();
//        when(userService.findUserByUsername(anyString())).thenReturn(user);
//        List<Booking> bookings = List.of(new Booking());
//        when(bookingRepository.findAllBookingsByUser(anyString())).thenReturn(bookings);
//
//        List<Booking> result = bookingService.getAllBookingsByUser("admin");
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(bookingRepository, times(1)).findAllBookingsByUser(anyString());
//    }
//
//    @Test
//    void testGetAllBookingsByUserInvalidUser() {
//        when(userService.findUserByUsername(anyString())).thenReturn(null);
//
//        List<Booking> result = bookingService.getAllBookingsByUser("invalid-user");
//        assertNull(result);
//        verify(bookingRepository, times(0)).findAllBookingsByUser(anyString());
//    }
//
//    @Test
//    void testGetAllBookingsByResourceSuccess() {
//        List<Booking> bookings = List.of(new Booking());
//        when(bookingRepository.findAllBookingsByResource(anyInt())).thenReturn(bookings);
//
//        List<Booking> result = bookingService.getAllBookingsByResource("1");
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(bookingRepository, times(1)).findAllBookingsByResource(anyInt());
//    }
//
//    @Test
//    void testGetAllBookingsByResourceInvalidId() {
//        List<Booking> result = bookingService.getAllBookingsByResource("invalid-id");
//        assertNull(result);
//        verify(bookingRepository, times(0)).findAllBookingsByResource(anyInt());
//    }
//
//    @Test
//    void testGetAllBookings() {
//        List<Booking> bookings = List.of(new Booking());
//        when(bookingRepository.findAllBookings()).thenReturn(Collections.singletonMap(1, new Booking()));
//
//        List<Booking> result = bookingService.getAllBookings();
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(bookingRepository, times(1)).findAllBookings();
//    }

//}