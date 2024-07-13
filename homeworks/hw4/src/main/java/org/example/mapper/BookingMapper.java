package org.example.mapper;

import org.example.entity.Booking;
import org.example.model.BookingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "workplaceId", target = "workplaceId")
    @Mapping(source = "hallId", target = "hallId")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "user", target = "user")
    BookingDTO bookingToBookingDTO(Booking booking);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "workplaceId", target = "workplaceId")
    @Mapping(source = "hallId", target = "hallId")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "user", target = "user")
    Booking bookingDTOToBooking(BookingDTO bookingDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "workplaceId", target = "workplaceId")
    @Mapping(source = "hallId", target = "hallId")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "user", target = "user")
    List<BookingDTO> bookingsToBookingDTOs(List<Booking> bookings);
}
