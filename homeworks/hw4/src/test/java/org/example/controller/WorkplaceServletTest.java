//package org.example.servlet;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ReadListener;
//import jakarta.servlet.ServletInputStream;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.example.entity.Workplace;
//import org.example.mapper.WorkplaceMapper;
//import org.example.model.WorkplaceDTO;
//import org.example.service.WorkplaceService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.mockito.Mockito.*;
//
//class WorkplaceServletTest {
//
//    private WorkplaceService workplaceService;
//    private WorkplaceServlet workplaceServlet;
//    private HttpServletRequest request;
//    private HttpServletResponse response;
//    private StringWriter responseWriter;
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setUp() {
//        workplaceService = mock(WorkplaceService.class);
//        workplaceServlet = new WorkplaceServlet();
//        workplaceServlet.workplaceService = workplaceService;
//        workplaceServlet.workplaceMapper = WorkplaceMapper.INSTANCE;
//        workplaceServlet.objectMapper = new ObjectMapper();
//        request = mock(HttpServletRequest.class);
//        response = mock(HttpServletResponse.class);
//        responseWriter = new StringWriter();
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test
//    void testDoGetAllWorkplaces() throws Exception {
//        when(request.getParameter("id")).thenReturn(null);
//        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
//
//        List<Workplace> workplaces = new ArrayList<>();
//
//        workplaces.add(Workplace.builder()
//                .id(Integer.valueOf("1"))
//                .description("Workplace 1")
//                .build());
//
//        workplaces.add(Workplace.builder()
//                .id(Integer.valueOf("2"))
//                .description("Workplace 2")
//                .build());
//
//        when(workplaceService.getAllWorkplaces()).thenReturn(workplaces);
//
//        workplaceServlet.doGet(request, response);
//
//        verify(response).setContentType("application/json");
//        verify(response).setCharacterEncoding("UTF-8");
//
//        String jsonResponse = responseWriter.toString();
//        assertFalse(jsonResponse.isEmpty());
//    }
//
//    @Test
//    void testDoGetWorkplaceById() throws Exception {
//        String id = "1";
//        when(request.getParameter("id")).thenReturn(id);
//        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
//
//        Workplace workplace = Workplace.builder()
//                .id(Integer.valueOf(id))
//                .description("Workplace 1")
//                .build();
//
//        when(workplaceService.getWorkplaceById(id)).thenReturn(workplace);
//
//        workplaceServlet.doGet(request, response);
//
//        verify(response).setContentType("application/json");
//        verify(response).setCharacterEncoding("UTF-8");
//
//        String jsonResponse = responseWriter.toString();
//        assertFalse(jsonResponse.isEmpty());
//    }
//
//    @Test
//    void testDoPutValidWorkplace() throws Exception {
//        String id = "1";
//        WorkplaceDTO workplaceDTO = WorkplaceDTO.builder()
//                .description("Updated Workplace")
//                .build();
//
//        Workplace workplace = Workplace.builder()
//                .id(Integer.valueOf(id))
//                .description("Updated Workplace")
//                .build();
//
//        when(request.getParameter("id")).thenReturn(id);
//        when(workplaceService.updateWorkplace(anyString(), anyString())).thenReturn(workplace);
//        when(request.getInputStream()).thenReturn(createServletInputStream(objectMapper.writeValueAsString(workplaceDTO)));
//        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
//
//        workplaceServlet.doPut(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_OK);
//    }
//
//    @Test
//    void testDoDeleteValidWorkplace() throws Exception {
//        String id = "1";
//        when(request.getParameter("id")).thenReturn(id);
//
//        Workplace workplace = Workplace.builder()
//                .id(Integer.valueOf(id))
//                .description("Workplace 1")
//                .build();
//
//        when(workplaceService.deleteWorkplace(id)).thenReturn(workplace);
//        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
//
//        workplaceServlet.doDelete(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_OK);
//    }
//
//    private ServletInputStream createServletInputStream(String data) {
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.getBytes());
//
//        return new ServletInputStream() {
//            @Override
//            public boolean isFinished() {
//                return byteArrayInputStream.available() == 0;
//            }
//
//            @Override
//            public boolean isReady() {
//                return true;
//            }
//
//            @Override
//            public void setReadListener(ReadListener readListener) {}
//
//            @Override
//            public int read() throws IOException {
//                return byteArrayInputStream.read();
//            }
//        };
//    }
//}