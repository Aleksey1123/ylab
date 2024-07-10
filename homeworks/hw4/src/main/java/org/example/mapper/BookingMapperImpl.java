package org.example.mapper;

import org.example.entity.Booking;
import org.example.entity.ConferenceHall;
import org.example.model.BookingDTO;
import org.example.model.ConferenceHallDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public List<BookingDTO> bookingsToBookingDTOs(List<Booking> bookings) {
        if (bookings == null) {
            return null;
        } else {
            List<BookingDTO> list = new ArrayList(bookings.size());
            Iterator var3 = bookings.iterator();

            while(var3.hasNext()) {
                Booking booking = (Booking) var3.next();
                list.add(this.bookingToBookingDTO(booking));
            }

            return list;
        }
    }
}
