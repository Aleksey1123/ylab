package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.ConferenceHall;
import org.example.mapper.ConferenceHallMapper;
import org.example.model.ConferenceHallDTO;
import org.example.service.ConferenceHallService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class ConferenceHallServletTest {

    private ConferenceHallService conferenceHallService;
    private ConferenceHallServlet conferenceHallServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter responseWriter;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        conferenceHallService = mock(ConferenceHallService.class);
        conferenceHallServlet = new ConferenceHallServlet();
        conferenceHallServlet.conferenceHallService = conferenceHallService;
        conferenceHallServlet.conferenceHallMapper = ConferenceHallMapper.INSTANCE;
        conferenceHallServlet.objectMapper = new ObjectMapper();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        responseWriter = new StringWriter();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testDoGetAllConferenceHalls() throws Exception {
        when(request.getParameter("id")).thenReturn(null);
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        List<ConferenceHall> conferenceHalls = new ArrayList<>();
        conferenceHalls.add(ConferenceHall.builder()
                .id(Integer.valueOf("1"))
                .description("Conference Hall 1")
                .size(50)
                .build());

        conferenceHalls.add(ConferenceHall.builder()
                .id(Integer.valueOf("2"))
                .description("Conference Hall 2")
                .size(100)
                .build());

        when(conferenceHallService.getAllConferenceHalls()).thenReturn(conferenceHalls);

        conferenceHallServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String jsonResponse = responseWriter.toString();
        assertFalse(jsonResponse.isEmpty());
    }

    @Test
    void testDoGetConferenceHallById() throws Exception {
        String id = "1";
        when(request.getParameter("id")).thenReturn(id);
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        ConferenceHall conferenceHall = ConferenceHall.builder()
                .id(Integer.valueOf(id))
                .description("Conference Hall 1")
                .size(50)
                .build();

        when(conferenceHallService.getConferenceHallById(id)).thenReturn(conferenceHall);

        conferenceHallServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String jsonResponse = responseWriter.toString();
        assertFalse(jsonResponse.isEmpty());
    }

    @Test
    void testDoPutValidConferenceHall() throws Exception {
        String id = "1";
        ConferenceHallDTO conferenceHallDTO = ConferenceHallDTO.builder()
                .description("Updated Conference Hall")
                .size(200)
                .build();

        ConferenceHall conferenceHall = ConferenceHall.builder()
                .id(Integer.valueOf(id))
                .description("Updated Conference Hall")
                .size(200)
                .build();

        when(request.getParameter("id")).thenReturn(id);
        when(conferenceHallService.updateConferenceHall(anyString(), anyString(), anyString())).thenReturn(conferenceHall);
        when(request.getInputStream()).thenReturn(createServletInputStream(objectMapper.writeValueAsString(conferenceHallDTO)));
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        conferenceHallServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testDoDeleteValidConferenceHall() throws Exception {
        String id = "1";
        when(request.getParameter("id")).thenReturn(id);

        ConferenceHall conferenceHall = ConferenceHall.builder()
                .id(Integer.valueOf(id))
                .description("Conference Hall 1")
                .size(50)
                .build();

        when(conferenceHallService.deleteConferenceHall(id)).thenReturn(conferenceHall);
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        conferenceHallServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    private ServletInputStream createServletInputStream(String data) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.getBytes());

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {}

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }
}