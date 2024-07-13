package org.example.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//class BookingServletTest {
//
//    private BookingService bookingService;
//    private BookingServlet bookingServlet;
//    private HttpServletRequest request;
//    private HttpServletResponse response;
//    private StringWriter responseWriter;
//
//    @BeforeEach
//    void setUp() {
//        bookingService = mock(BookingService.class);
//        bookingServlet = new BookingServlet();
//        bookingServlet.bookingService = bookingService;
//        bookingServlet.bookingMapper = BookingMapper.INSTANCE;
//        bookingServlet.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
//        request = mock(HttpServletRequest.class);
//        response = mock(HttpServletResponse.class);
//        responseWriter = new StringWriter();
//    }
//
//    @Test
//    void testDoGetAllBookings() throws Exception {
//        when(request.getParameter("action")).thenReturn("all");
//        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
//
//        List<Booking> bookings = List.of(new Booking());
//        when(bookingService.getAllBookings()).thenReturn(bookings);
//
//        bookingServlet.doGet(request, response);
//
//        verify(response).setContentType("application/json");
//        verify(response).setCharacterEncoding("UTF-8");
//
//        String jsonResponse = responseWriter.toString();
//        assertFalse(jsonResponse.isEmpty());
//    }
//
//    @Test
//    void testDoGetBookingsByResource() throws Exception {
//        when(request.getParameter("action")).thenReturn("resource");
//        when(request.getParameter("value")).thenReturn("1");
//        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
//
//        List<Booking> bookings = List.of(new Booking());
//        when(bookingService.getAllBookingsByResource(anyString())).thenReturn(bookings);
//
//        bookingServlet.doGet(request, response);
//
//        verify(response).setContentType("application/json");
//        verify(response).setCharacterEncoding("UTF-8");
//
//        String jsonResponse = responseWriter.toString();
//        assertFalse(jsonResponse.isEmpty());
//    }
//
//
//    @Test
//    void testDoDeleteValidId() throws Exception {
//        when(request.getParameter("id")).thenReturn("1");
//        Booking booking = new Booking();
//        when(bookingService.cancelBooking(anyString())).thenReturn(booking);
//        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
//
//        bookingServlet.doDelete(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_OK);
//    }
//
//    @Test
//    void testDoDeleteInvalidId() throws Exception {
//        when(request.getParameter("id")).thenReturn(null);
//        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
//
//        bookingServlet.doDelete(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        String jsonResponse = responseWriter.toString();
//        assertTrue(jsonResponse.contains("Invalid id parameter."));
//    }
//}
