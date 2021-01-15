package controller.userManagement.adminManagement;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import controller.RequestParametersException;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.Moderator;
import model.dao.ModeratorDAO;
import org.jetbrains.annotations.NotNull;

/**
 * This servlet add more moderators to the response.
 */
@WebServlet(urlPatterns = {"/get-more-moderators"})
public class GetMoreModeratorsServlet extends HttpServlet {
    public static final int MODERATORS_PER_REQUEST_DEFAULT = 4;

    /**
     * this method manages Post request calling doGet method.
     *
     * @param req a HttpServletRequest
     * @param resp an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }

    /**
     * this method manages Get requests.
     *
     * @param req a HttpServletRequest
     * @param resp an HttpServletResponse
     * @throws ServletException if an exception is occurred
     * @throws IOException if an exception is occurred
     */
    @Override
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)
            throws ServletException, IOException {
        int startingIndex;

        JsonObject moderator;
        JsonArray newModerators = new JsonArray();
        int moderatorsPerRequest = (req.getParameter("moderatorsPerRequest") != null)
                ? (Integer.parseInt(req.getParameter("moderatorsPerRequest")))
                : (MODERATORS_PER_REQUEST_DEFAULT);
        try {
            startingIndex = Integer.parseInt(req.getParameter("startingIndex").trim());
        } catch (NumberFormatException e) {
            throw new RequestParametersException(
                    "Error in the parameters, moderator number must be an integer"
            );
        }
        ModeratorDAO md = new ModeratorDAO();
        ArrayList<Moderator> moderatorList;


        moderatorList = md.doRetrieveByUsernameFragment(
                "%", startingIndex, moderatorsPerRequest
        );
        JsonObject responseObject = new JsonObject();
        ArrayList<Moderator> moderatorListFull = md.doRetrieveAll();

        for (int i = 0; i < moderatorsPerRequest && i < moderatorList.size(); i++) {
            moderator = new JsonObject();
            moderator.addProperty("username", moderatorList.get(i).getUsername());
            moderator.addProperty("contractTime", moderatorList.get(i).getContractTime());
            newModerators.add(moderator);
        }
        responseObject.add("newModerators", newModerators);
        responseObject.addProperty("newMaxPages",
                Math.max(Math.ceil(moderatorListFull.size() / (double) moderatorsPerRequest), 1));
        resp.getWriter().println(responseObject);
        resp.flushBuffer();
    }

}
