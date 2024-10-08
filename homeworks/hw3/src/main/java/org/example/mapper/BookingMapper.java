package org.example.mapper;

import org.example.entity.Booking;
import org.example.model.BookingDTO;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    BookingDTO bookingToBookingDTO(Booking booking);

    Booking bookingDTOToBooking(BookingDTO bookingDTO);

    List<BookingDTO> bookingsToBookingDTOs(List<Booking> bookings);
}
