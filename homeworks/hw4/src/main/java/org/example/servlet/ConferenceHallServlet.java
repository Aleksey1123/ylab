package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.annotation.Loggable;
import org.example.entity.ConferenceHall;
import org.example.mapper.ConferenceHallMapper;
import org.example.model.ConferenceHallDTO;
import org.example.service.ConferenceHallService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Loggable
@WebServlet(name = "ConferenceHallServlet", urlPatterns = "/conferenceHall")
public class ConferenceHallServlet extends HttpServlet {

    protected ConferenceHallService conferenceHallService = new ConferenceHallService();
    protected ConferenceHallMapper conferenceHallMapper = ConferenceHallMapper.INSTANCE;
    protected ObjectMapper objectMapper = new ObjectMapper();

    /** This method returns a ConferenceHall specified by id parameter or returns
     * all existing ConferenceHalls if id parameter is null.
     * Requested Parameter:
     * ?id=******* - id of any existing ConferenceHall
     * or it will return all existing ConferenceHalls
     *
     * **/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");

        if (id != null)
            findById(resp, id);
        else findAll(resp);
    }

    /** This method are used by doGet(). It returns an existing ConferenceHall specified by id. **/
    private void findById(HttpServletResponse resp, String id) throws IOException {

        ConferenceHall conferenceHall = conferenceHallService.getConferenceHallById(id);
        if (conferenceHall == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else {
            ConferenceHallDTO conferenceHallDTO =
                    conferenceHallMapper.conferenceHallToConferenceHallDTO(conferenceHall);
            String conferenceHallString =
                    objectMapper.writeValueAsString(conferenceHallDTO);

            printJson(resp, conferenceHallString);
        }
    }

    /** This method are used by doGet(). It returns a json of all ConferenceHalls. **/
    private void findAll(HttpServletResponse resp) throws IOException {

        List<ConferenceHall> conferenceHalls = conferenceHallService.getAllConferenceHalls();
        List<ConferenceHallDTO> conferenceHallDTOs =
                conferenceHallMapper.conferenceHallsToConferenceHallDTOs(conferenceHalls);
        String conferenceHallString = objectMapper.writeValueAsString(conferenceHallDTOs);

        printJson(resp, conferenceHallString);
    }

    /** This method returns a json of a given string. **/
    private void printJson(HttpServletResponse resp, String stringObject) throws IOException {

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(stringObject);
        out.flush();
    }

    /** This method creates a new ConferenceHall via request body data.
     *  Requested Body:
     * {
     *     "description": ******* - description of a future ConferenceHall
     *     "size": ******* - size of an existing ConferenceHall
     * }
     *
     * **/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ConferenceHallDTO conferenceHallDTO =
                objectMapper.readValue(req.getInputStream(), ConferenceHallDTO.class);
        ConferenceHall savedConferenceHall =
                conferenceHallService.createConferenceHall(conferenceHallDTO.getDescription(),
                        conferenceHallDTO.getSize().toString());
        resp.setStatus(HttpServletResponse.SC_CREATED);
        objectMapper.writeValue(resp.getOutputStream(), savedConferenceHall);
    }

    /** This method updates an existing ConferenceHall via request body data.
     *  Requested Body:
     * {
     *     "description": ******* - description of an existing ConferenceHall
     *     "size": ******* - size of an existing ConferenceHall
     * }
     *
     * **/
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        if (id != null) {
            ConferenceHallDTO conferenceHallDTO =
                    objectMapper.readValue(req.getInputStream(), ConferenceHallDTO.class);
            ConferenceHall updatedConferenceHall =
                    conferenceHallService.updateConferenceHall(id,
                            conferenceHallDTO.getDescription(),
                            conferenceHallDTO.getSize().toString());
            if (updatedConferenceHall != null)
                resp.setStatus(HttpServletResponse.SC_OK);
            else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    /** This method deletes a ConferenceHall specified by id.
     * Requested Parameter:
     * ?id=******* - id of an existing ConferenceHall
     *
     * **/
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        if (id != null) {
            ConferenceHall deletedConferenceHall =
                    conferenceHallService.deleteConferenceHall(id);
            if (deletedConferenceHall != null)
                resp.setStatus(HttpServletResponse.SC_OK);
            else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
