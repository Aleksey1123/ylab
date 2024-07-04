package org.example.mapper;

import org.example.entity.Booking;
import org.example.model.BookingDTO;

public class BookingMapperImpl implements BookingMapper {
    public BookingMapperImpl() {
    }

    public BookingDTO bookingToBookingDTO(Booking booking) {
        if (booking == null) {
            return null;
        } else {
            BookingDTO.BookingDTOBuilder bookingDTO = BookingDTO.builder();
            bookingDTO.id(booking.getId());
            bookingDTO.workplaceId(booking.getWorkplaceId());
            bookingDTO.hallId(booking.getHallId());
            bookingDTO.startTime(booking.getStartTime());
            bookingDTO.endTime(booking.getEndTime());
            bookingDTO.user(booking.getUser());
            return bookingDTO.build();
        }
    }

    public Booking bookingDTOToBooking(BookingDTO bookingDTO) {
        if (bookingDTO == null) {
            return null;
        } else {
            Booking.BookingBuilder booking = Booking.builder();
            booking.id(bookingDTO.getId());
            booking.workplaceId(bookingDTO.getWorkplaceId());
            booking.hallId(bookingDTO.getHallId());
            booking.startTime(bookingDTO.getStartTime());
            booking.endTime(bookingDTO.getEndTime());
            booking.user(bookingDTO.getUser());
            return booking.build();
        }
    }
}
