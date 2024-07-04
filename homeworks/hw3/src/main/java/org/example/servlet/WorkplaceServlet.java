package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.annotation.Loggable;
import org.example.entity.Workplace;
import org.example.mapper.WorkplaceMapper;
import org.example.model.WorkplaceDTO;
import org.example.service.WorkplaceService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Loggable
@WebServlet(name = "WorkplaceServlet", urlPatterns = "/workplace")
public class WorkplaceServlet extends HttpServlet {

    private WorkplaceService workplaceService = new WorkplaceService();
    private WorkplaceMapper workplaceMapper = WorkplaceMapper.INSTANCE;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");

        if (id != null)
            findById(resp, id);
        else findAll(resp);
    }

    private void findById(HttpServletResponse resp, String id) throws IOException {

        Workplace workplace = workplaceService.getWorkplaceById(id);
        if (workplace == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else {
            WorkplaceDTO workplaceDTO = workplaceMapper.workplaceToWorkplaceDTO(workplace);
            String employeeString = objectMapper.writeValueAsString(workplaceDTO);

            printJson(resp, employeeString);
        }
    }

    private void findAll(HttpServletResponse resp) throws IOException {

        List<Workplace> workplaces = workplaceService.getAllWorkplaces();
        List<WorkplaceDTO> workplaceDTOs = workplaceMapper.workplacesToWorkplaceDTOs(workplaces);
        String workplacesString = objectMapper.writeValueAsString(workplaceDTOs);

        printJson(resp, workplacesString);
    }

    private void printJson(HttpServletResponse resp, String stringObject) throws IOException {

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(stringObject);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        WorkplaceDTO workplaceDTO =
                objectMapper.readValue(req.getInputStream(), WorkplaceDTO.class);
        Workplace savedWorkplace = workplaceService.createWorkplace(workplaceDTO.getDescription());
        resp.setStatus(HttpServletResponse.SC_CREATED);
        objectMapper.writeValue(resp.getOutputStream(), savedWorkplace);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        if (id != null) {
            WorkplaceDTO workplaceDTO = objectMapper.readValue(req.getInputStream(), WorkplaceDTO.class);
            Workplace updatedWorkplace = workplaceService.updateWorkplace(id, workplaceDTO.getDescription());
            if (updatedWorkplace != null)
                resp.setStatus(HttpServletResponse.SC_OK);
            else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        if (id != null) {
            Workplace deletedWorkplace = workplaceService.deleteWorkplace(id);
            if (deletedWorkplace != null)
                resp.setStatus(HttpServletResponse.SC_OK);
            else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
