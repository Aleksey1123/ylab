package org.example.mapper;

import org.example.entity.Booking;
import org.example.model.BookingDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingDTO bookingToBookingDTO(Booking booking);

    Booking bookingDTOToBooking(BookingDTO bookingDTO);

    List<BookingDTO> bookingsToBookingDTOs(List<Booking> bookings);
}
