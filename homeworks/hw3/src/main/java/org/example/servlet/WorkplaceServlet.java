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

    protected WorkplaceService workplaceService = new WorkplaceService();
    protected WorkplaceMapper workplaceMapper = WorkplaceMapper.INSTANCE;
    protected ObjectMapper objectMapper = new ObjectMapper();

    /** This method returns a workplace specified by id parameter or returns
     * all existing workplaces if id parameter is null.
     * Requested Parameter:
     * ?id=******* - id of any existing workplace
     * or it will return all existing workplaces
     *
     * **/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");

        if (id != null)
            findById(resp, id);
        else findAll(resp);
    }

    /** This method are used by doGet(). It returns an existing workplace specified by id. **/
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

    /** This method are used by doGet(). It returns a json of all workplaces. **/
    private void findAll(HttpServletResponse resp) throws IOException {

        List<Workplace> workplaces = workplaceService.getAllWorkplaces();
        List<WorkplaceDTO> workplaceDTOs = workplaceMapper.workplacesToWorkplaceDTOs(workplaces);
        String workplacesString = objectMapper.writeValueAsString(workplaceDTOs);

        printJson(resp, workplacesString);
    }

    /** This method returns a json of a given string. **/
    private void printJson(HttpServletResponse resp, String stringObject) throws IOException {

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(stringObject);
        out.flush();
    }

    /** This method creates a new workplace via request body data.
     *  Requested Body:
     * {
     *     "description": ******* - description of a future workplace
     * }
     *
     * **/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        WorkplaceDTO workplaceDTO =
                objectMapper.readValue(req.getInputStream(), WorkplaceDTO.class);
        Workplace savedWorkplace = workplaceService.createWorkplace(workplaceDTO.getDescription());
        if (savedWorkplace != null) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(resp.getOutputStream(), savedWorkplace);
        }
        else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printJson(resp, "");
        }
    }

    /** This method updates an existing workplace via request body data.
     *  Requested Body:
     * {
     *     "description": ******* - description of an existing workplace
     * }
     *
     * **/
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

    /** This method deletes a workplace specified by id.
     * Requested Parameter:
     * ?id=******* - id of an existing workplace
     *
     * **/
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
