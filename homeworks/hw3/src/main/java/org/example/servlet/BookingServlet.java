package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.annotation.Loggable;
import org.example.entity.Booking;
import org.example.mapper.BookingMapper;
import org.example.model.BookingDTO;
import org.example.model.BookingPostRequest;
import org.example.service.BookingService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Loggable
@WebServlet(name = "BookingServlet", urlPatterns = "/booking")
public class BookingServlet extends HttpServlet {

    protected BookingService bookingService = new BookingService(UserServlet.userService);
    protected BookingMapper bookingMapper = BookingMapper.INSTANCE;
    protected ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /** This method returns a list of bookings filtered by given parameters.
     *
     * Requested Parameters:
     * ?action=******* - can be [all, resource, date, user]
     * ?value=***** - can be [null, resourceType, date, username]
     *
     * **/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        String value = req.getParameter("value");

        if (action.equals("all"))
            findAll(resp);
        else if (action.equals("resource"))
            findAllByResource(resp, value);
        else if (action.equals("date"))
            findAllByDate(resp, value);
        else if (action.equals("user"))
            findAllByUser(resp, value);
        else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printJson(resp, "{\"error\":\"Invalid parameters.\"}");
        }
    }

    /** This method are used by doGet(). It returns a json of all bookings. **/
    private void findAll(HttpServletResponse resp) throws IOException {

        List<Booking> bookings = bookingService.getAllBookings();
        List<BookingDTO> bookingDTOs =
                bookingMapper.bookingsToBookingDTOs(bookings);
        String bookingString = objectMapper.writeValueAsString(bookingDTOs);

        printJson(resp, bookingString);
    }

    /** This method are used by doGet(). It returns a json of all bookings filtered by resourceId. **/
    private void findAllByResource(HttpServletResponse resp, String resourceId) throws IOException {

        List<Booking> bookings = bookingService.getAllBookingsByResource(resourceId);
        List<BookingDTO> bookingDTOs =
                bookingMapper.bookingsToBookingDTOs(bookings);
        String bookingString = objectMapper.writeValueAsString(bookingDTOs);

        printJson(resp, bookingString);
    }

    /** This method are used by doGet(). It returns a json of all bookings filtered by user. **/
    private void findAllByUser(HttpServletResponse resp, String username) throws IOException {

        List<Booking> bookings = bookingService.getAllBookingsByUser(username);
        List<BookingDTO> bookingDTOs =
                bookingMapper.bookingsToBookingDTOs(bookings);
        String bookingString = objectMapper.writeValueAsString(bookingDTOs);

        printJson(resp, bookingString);
    }

    /** This method are used by doGet(). It returns a json of all bookings filtered by date. **/
    private void findAllByDate(HttpServletResponse resp, String time) throws IOException {

        List<Booking> bookings = bookingService.getAllBookingsByDate(time);
        List<BookingDTO> bookingDTOs =
                bookingMapper.bookingsToBookingDTOs(bookings);
        String bookingString = objectMapper.writeValueAsString(bookingDTOs);

        printJson(resp, bookingString);
    }

    /** This method returns a json of a given string. **/
    private void printJson(HttpServletResponse resp, String stringObject) throws IOException {

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(stringObject);
        out.flush();
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
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BookingPostRequest bookingPostRequest =
                objectMapper.readValue(req.getInputStream(), BookingPostRequest.class);
        Booking savedBooking =
                bookingService.makeBooking(bookingPostRequest);
        if (savedBooking != null) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(resp.getOutputStream(), savedBooking);
        }
        else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printJson(resp, "{\"error\":\"Invalid booking body.\"}");
        }
    }

    /** This method deletes an existing booking by id. Requires an id parameter.
     *
     * Requested Parameter:
     * ?id=******* - id of any Booking
     *
     * **/
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        if (id != null) {
            Booking deletedBooking =
                    bookingService.cancelBooking(id);
            if (deletedBooking != null)
                resp.setStatus(HttpServletResponse.SC_OK);
            else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printJson(resp, "{\"error\":\"Invalid id parameter.\"}");
        }
    }
}
