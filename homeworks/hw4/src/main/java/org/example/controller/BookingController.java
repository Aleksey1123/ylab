package org.example.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.example.entity.Booking;
import org.example.mapper.BookingMapper;
import org.example.model.BookingDTO;
import org.example.model.BookingPostRequest;
import org.example.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "Booking")
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    /** This method returns a list of bookings filtered by given parameters.
     *
     * Requested Parameters:
     * ?action=******* - can be [all, resource, date, user]
     * ?value=***** - can be [null, resourceType, date, username]
     *
     * **/
    @GetMapping
    public ResponseEntity<?> getAllBookings(
            @RequestParam(name = "action", defaultValue = "all") String action,
            @RequestParam(name = "value", required = false) String value
    ) throws SQLException {

        switch (action) {
            case "all" -> {
                return ResponseEntity.ok(findAll());
            }
            case "resource" -> {
                return ResponseEntity.ok(findAllByResource(value));
            }
            case "date" -> {
                return ResponseEntity.ok(findAllByDate(value));
            }
            case "user" -> {
                return ResponseEntity.ok(findAllByUser(value));
            }
        }
        return ResponseEntity.badRequest().body("Invalid action parameter.");
    }

    /** This method are used by doGet(). It returns a json of all bookings. **/
    private List<BookingDTO> findAll() throws SQLException {

        List<Booking> bookings = bookingService.getAllBookings();
        return bookingMapper.bookingsToBookingDTOs(bookings);
    }

    /** This method are used by doGet(). It returns a json of all bookings filtered by resourceId. **/
    private List<BookingDTO> findAllByResource(String resourceId) throws SQLException {

        List<Booking> bookings = bookingService.getAllBookingsByResource(resourceId);
        return bookingMapper.bookingsToBookingDTOs(bookings);
    }

    /** This method are used by doGet(). It returns a json of all bookings filtered by user. **/
    private List<BookingDTO> findAllByUser(String username) throws SQLException {

        List<Booking> bookings = bookingService.getAllBookingsByUser(username);
        return bookingMapper.bookingsToBookingDTOs(bookings);
    }

    /** This method are used by doGet(). It returns a json of all bookings filtered by date. **/
    private List<BookingDTO> findAllByDate(String dateTime) throws SQLException {

        List<Booking> bookings = bookingService.getAllBookingsByDate(dateTime);
        return bookingMapper.bookingsToBookingDTOs(bookings);
    }

    /** This method creates a new booking. Requires request body.
     *
     * Requested Body:
     * {
     * 	   "resourceId": 3, - any id of an existing workplace or hall
     *     "resourceType": "W", - can be W(workplace) or H(hall)
     *     "startDateTimeString": "2024-06-21T15:00:00", - any LocalDateTime
     *     "endDateTimeString": "2024-06-21T16:00:00" - any LocalDateTime
     * }
     *
     * **/
    @PostMapping
    public ResponseEntity<?> addBooking(@RequestBody BookingPostRequest bookingPostRequest) throws SQLException {

        Booking savedBooking = bookingService.makeBooking(bookingPostRequest);
        BookingDTO savedBookingDTO = bookingMapper.bookingToBookingDTO(savedBooking);

        if (savedBookingDTO != null) {
            return ResponseEntity.ok(savedBookingDTO);
        }

        return ResponseEntity.badRequest().body("This time has been already booked.");
    }

    /** This method deletes an existing booking by id. Requires an id parameter.
     *
     * Requested Parameter:
     * ?id=******* - id of any Booking
     *
     * **/
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable String id) throws SQLException {

        Booking deletedBooking = bookingService.cancelBooking(id);
        BookingDTO deletedBookingDTO = bookingMapper
                .bookingToBookingDTO(deletedBooking);

        return ResponseEntity.ok(deletedBookingDTO);
    }
}
