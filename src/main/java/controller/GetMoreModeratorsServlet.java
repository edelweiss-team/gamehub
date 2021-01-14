package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.bean.Moderator;
import model.dao.ModeratorDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/get-more-moderators"})
public class GetMoreModeratorsServlet extends HttpServlet {
    public static final int MODERATORS_PER_REQUEST_DEFAULT = 4;

    /**
     *This method calls the doGet method.
     *
     * @param req the HttpServletRequest from the client
     * @param resp the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    /**
     * Add more moderators to the response.
     *
     * @param req a HttpServletRequest
     * @param resp an HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int startingIndex;
        JsonObject responseObject = new JsonObject(), moderator;
        JsonArray newModerators = new JsonArray();
        int moderatorsPerRequest = (req.getParameter("moderatorsPerRequest") != null)?
                (Integer.parseInt(req.getParameter("moderatorsPerRequest"))):(MODERATORS_PER_REQUEST_DEFAULT);
        try{
            startingIndex = Integer.parseInt(req.getParameter("startingIndex").trim());
        }
        catch (NumberFormatException e){
            throw new RequestParametersException("Error in the parameters, moderator number must be an integer");
        }
        ModeratorDAO md = new ModeratorDAO();
        ArrayList<Moderator> moderatorList;
        ArrayList<Moderator> moderatorListFull = md.doRetrieveAll();

        moderatorList = md.doRetrieveByUsernameFragment("%", startingIndex, moderatorsPerRequest);


        for(int i = 0; i < moderatorsPerRequest && i < moderatorList.size(); i++){
            moderator = new JsonObject();
            moderator.addProperty("username", moderatorList.get(i).getUsername());
            moderator.addProperty("contractTime", moderatorList.get(i).getContractTime());
            newModerators.add(moderator);
        }
        responseObject.add("newOperators", newModerators);
        responseObject.addProperty("newMaxPages",
                Math.max(Math.ceil(moderatorListFull.size()/(double)moderatorsPerRequest), 1));
        resp.getWriter().println(responseObject);
        resp.flushBuffer();
    }

}
